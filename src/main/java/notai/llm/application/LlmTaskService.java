package notai.llm.application;

import static java.util.stream.Collectors.groupingBy;
import lombok.RequiredArgsConstructor;
import notai.annotation.domain.Annotation;
import notai.annotation.domain.AnnotationRepository;
import notai.client.ai.AiClient;
import notai.client.ai.request.LlmTaskRequest;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import notai.llm.application.command.LlmTaskSubmitCommand;
import notai.llm.application.command.SummaryAndProblemUpdateCommand;
import notai.llm.application.result.LlmTaskSubmitResult;
import notai.llm.domain.LlmTask;
import notai.llm.domain.LlmTaskRepository;
import notai.problem.domain.Problem;
import notai.problem.domain.ProblemRepository;
import notai.summary.domain.Summary;
import notai.summary.domain.SummaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * SummaryService 와 ExamService 는 엔티티와 관련된 로직만 처리하고
 * AI 요약 및 문제 생성 요청은 여기서 처리하는 식으로 생각했습니다.
 * AI 서버와의 통신은 별도 클래스에서 처리합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class LlmTaskService {

    private final LlmTaskRepository llmTaskRepository;
    private final DocumentRepository documentRepository;
    private final SummaryRepository summaryRepository;
    private final ProblemRepository problemRepository;
    private final AnnotationRepository annotationRepository;
    private final AiClient aiClient;

    public LlmTaskSubmitResult submitTasks(LlmTaskSubmitCommand command) { // TODO: 페이지 번호 검증 추가
        Document foundDocument = documentRepository.getById(command.documentId());
        List<Annotation> annotations = annotationRepository.findByDocumentId(command.documentId());

        Map<Integer, List<Annotation>> annotationsByPage =
                annotations.stream().collect(groupingBy(Annotation::getPageNumber));

        command.pages().forEach(pageNumber -> {
            submitPageTask(pageNumber, annotationsByPage, foundDocument);
        });

        return LlmTaskSubmitResult.of(command.documentId(), LocalDateTime.now());
    }

    private void submitPageTask(Integer pageNumber, Map<Integer, List<Annotation>> annotationsByPage, Document foundDocument) {
        String annotationContents = annotationsByPage.getOrDefault(
                pageNumber,
                List.of()
        ).stream().map(Annotation::getContent).collect(Collectors.joining(", "));

        // Todo OCR, STT 결과 전달
        UUID taskId = sendRequestToAIServer("ocrText", "stt", annotationContents);

        Optional<Summary> foundSummary = summaryRepository.findByDocumentAndPageNumber(foundDocument, pageNumber);
        Optional<Problem> foundProblem = problemRepository.findByDocumentAndPageNumber(foundDocument, pageNumber);

        if (foundSummary.isEmpty() && foundProblem.isEmpty()) {
            Summary summary = new Summary(foundDocument, pageNumber);
            Problem problem = new Problem(foundDocument, pageNumber);

            LlmTask taskRecord = new LlmTask(taskId, summary, problem);
            llmTaskRepository.save(taskRecord);
        }
        if (foundSummary.isPresent() && foundProblem.isPresent()) {
            LlmTask foundTaskRecord = llmTaskRepository.getBySummaryAndProblem(foundSummary.get(), foundProblem.get());
            llmTaskRepository.delete(foundTaskRecord);

            LlmTask taskRecord = new LlmTask(taskId, foundSummary.get(), foundProblem.get());
            llmTaskRepository.save(taskRecord);
        }
    }

    public Integer updateSummaryAndProblem(SummaryAndProblemUpdateCommand command) {
        LlmTask taskRecord = llmTaskRepository.getById(command.taskId());
        Summary foundSummary = summaryRepository.getById(taskRecord.getSummary().getId());
        Problem foundProblem = problemRepository.getById(taskRecord.getProblem().getId());

        taskRecord.completeTask();
        foundSummary.updateContent(command.summary());
        foundProblem.updateContent(command.problem());

        llmTaskRepository.save(taskRecord);
        summaryRepository.save(foundSummary);
        problemRepository.save(foundProblem);

        return foundSummary.getPageNumber();
    }

    private UUID sendRequestToAIServer(String ocrText, String stt, String keyboardNote) {
        return aiClient.submitLlmTask(LlmTaskRequest.of(ocrText, stt, keyboardNote)).taskId();
    }
}
