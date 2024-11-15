package notai.llm.application;

import notai.annotation.domain.Annotation;
import notai.annotation.domain.AnnotationRepository;
import notai.client.ai.AiClient;
import notai.client.ai.request.LlmTaskRequest;
import notai.client.ai.response.TaskResponse;
import notai.common.exception.type.NotFoundException;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import notai.folder.domain.Folder;
import notai.llm.application.command.LlmTaskSubmitCommand;
import notai.llm.application.command.SummaryAndProblemUpdateCommand;
import notai.llm.application.result.LlmTaskSubmitResult;
import notai.llm.domain.LlmTask;
import notai.llm.domain.LlmTaskRepository;
import notai.llm.domain.TaskStatus;
import notai.member.domain.Member;
import notai.member.domain.OauthId;
import notai.member.domain.OauthProvider;
import notai.ocr.domain.OCR;
import notai.ocr.domain.OCRRepository;
import notai.problem.domain.Problem;
import notai.problem.domain.ProblemRepository;
import notai.recording.domain.Recording;
import notai.stt.domain.Stt;
import notai.stt.domain.SttRepository;
import notai.sttTask.domain.SttTask;
import notai.summary.domain.Summary;
import notai.summary.domain.SummaryRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class LlmTaskServiceTest {

    @InjectMocks
    private LlmTaskService llmTaskService;

    @Mock
    private LlmTaskRepository llmTaskRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private SummaryRepository summaryRepository;

    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private AnnotationRepository annotationRepository;

    @Mock
    private SttRepository sttRepository;

    @Mock
    private OCRRepository ocrRepository;

    @Mock
    private AiClient aiClient;

    @Test
    void AI_기능_요청시_존재하지_않는_문서ID로_요청한_경우_예외_발생() {
        // given
        Long documentId = 1L;
        List<Integer> pages = List.of(1, 2, 3);
        LlmTaskSubmitCommand command = new LlmTaskSubmitCommand(documentId, pages);

        given(documentRepository.getById(anyLong())).willThrow(NotFoundException.class);

        // when & then
        assertAll(
                () -> assertThrows(NotFoundException.class, () -> llmTaskService.submitTasks(command)),
                () -> verify(documentRepository, times(1)).getById(documentId),
                () -> verify(llmTaskRepository, never()).save(any(LlmTask.class))
        );
    }

    @Test
    void AI_기능_요청_및_AI_클라이언트_테스트() {
        // given
        Long documentId = 1L;
        List<Integer> pages = List.of(1, 2);
        LlmTaskSubmitCommand command = new LlmTaskSubmitCommand(documentId, pages);

        Member member = new Member(new OauthId("12345", OauthProvider.KAKAO), "test@example.com", "TestUser");
        Folder folder = new Folder(member, "TestFolder");
        Document document = new Document(folder, member, "TestDocument", "http://example.com/test.pdf", 43);
        Recording recording = new Recording(document);

        UUID taskId = UUID.randomUUID();
        SttTask sttTask = new SttTask(taskId, TaskStatus.IN_PROGRESS, recording);
        List<Stt> stts = List.of(new Stt(sttTask));

        List<Annotation> annotations = List.of(
                new Annotation(document, 1, 10, 20, 100, 50, "Annotation 1"),
                new Annotation(document, 1, 30, 40, 80, 60, "Annotation 2"),
                new Annotation(document, 2, 50, 60, 120, 70, "Annotation 3")
        );

        List<OCR> ocrs = List.of(new OCR(document, 1, "TestDocumentContent"));

        TaskResponse taskResponse = new TaskResponse(taskId, "llm");

        given(documentRepository.getById(anyLong())).willReturn(document);
        given(annotationRepository.findByDocumentId(anyLong())).willReturn(annotations);
        given(sttRepository.findAllByDocumentIdAndPageNumber(any(), anyInt())).willReturn(stts);
        given(ocrRepository.findAllByDocumentIdAndPageNumber(any(), anyInt())).willReturn(ocrs);
        given(aiClient.submitLlmTask(any(LlmTaskRequest.class))).willReturn(taskResponse);
        given(llmTaskRepository.save(any(LlmTask.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        LlmTaskSubmitResult result = llmTaskService.submitTasks(command);

        // then
        assertAll(
                () -> verify(documentRepository, times(1)).getById(documentId),
                () -> verify(annotationRepository, times(1)).findByDocumentId(documentId),
                () -> verify(aiClient, times(2)).submitLlmTask(any(LlmTaskRequest.class)),
                () -> verify(llmTaskRepository, times(2)).save(any(LlmTask.class))
        );

        verify(aiClient).submitLlmTask(argThat(request ->
                request.keyboardNote().equals("Annotation 1, Annotation 2")));
        verify(aiClient).submitLlmTask(argThat(request ->
                request.keyboardNote().equals("Annotation 3")));
    }

    @Test
    void AI_서버에서_페이지별_작업이_완료되면_Summary와_Problem_업데이트() {
        // given
        UUID taskId = UUID.randomUUID();
        Long summaryId = 1L;
        Long problemId = 1L;
        String summaryContent = "요약 내용";
        String problemContent = "문제 내용";
        Integer pageNumber = 5;

        LlmTask taskRecord = mock(LlmTask.class);
        Summary summary = mock(Summary.class);
        Problem problem = mock(Problem.class);

        SummaryAndProblemUpdateCommand command = new SummaryAndProblemUpdateCommand(
                taskId,
                summaryContent,
                problemContent
        );

        given(llmTaskRepository.getById(any(UUID.class))).willReturn(taskRecord);
        given(summaryRepository.getById(anyLong())).willReturn(summary);
        given(problemRepository.getById(anyLong())).willReturn(problem);

        given(taskRecord.getSummary()).willReturn(summary);
        given(taskRecord.getProblem()).willReturn(problem);
        given(summary.getId()).willReturn(summaryId);
        given(problem.getId()).willReturn(problemId);
        given(summary.getPageNumber()).willReturn(pageNumber);

        // when
        Integer resultPageNumber = llmTaskService.updateSummaryAndProblem(command);

        // then
        assertAll(
                () -> verify(taskRecord).completeTask(),
                () -> verify(summary).updateContent(summaryContent),
                () -> verify(problem).updateContent(problemContent),
                () -> verify(llmTaskRepository, times(1)).save(taskRecord),
                () -> verify(summaryRepository, times(1)).save(summary),
                () -> verify(problemRepository, times(1)).save(problem),
                () -> assertEquals(pageNumber, resultPageNumber)
        );
    }
}
