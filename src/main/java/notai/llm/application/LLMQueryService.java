package notai.llm.application;

import static notai.common.exception.ErrorMessages.DOCUMENT_NOT_FOUND;
import static notai.common.exception.ErrorMessages.LLM_TASK_RESULT_ERROR;
import static notai.llm.domain.TaskStatus.COMPLETED;
import static notai.llm.domain.TaskStatus.IN_PROGRESS;
import static notai.llm.domain.TaskStatus.NOT_REQUESTED;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notai.common.exception.type.InternalServerErrorException;
import notai.common.exception.type.NotFoundException;
import notai.document.domain.DocumentRepository;
import notai.llm.application.command.LLMPageResultCommand;
import notai.llm.application.command.LLMPageStatusCommand;
import notai.llm.application.result.LLMAllPagesResult;
import notai.llm.application.result.LLMAllPagesResult.LLMContent;
import notai.llm.application.result.LLMAllPagesResult.LLMResult;
import notai.llm.application.result.LLMOverallStatusResult;
import notai.llm.application.result.LLMPageResult;
import notai.llm.application.result.LLMPageStatusResult;
import notai.llm.domain.TaskStatus;
import notai.llm.query.LLMQueryRepository;
import notai.problem.domain.ProblemRepository;
import notai.problem.query.result.ProblemPageContentResult;
import notai.summary.domain.SummaryRepository;
import notai.summary.query.result.SummaryPageContentResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LLMQueryService {

    private final LLMQueryRepository llmQueryRepository;
    private final DocumentRepository documentRepository;
    private final SummaryRepository summaryRepository;
    private final ProblemRepository problemRepository;

    public LLMOverallStatusResult fetchOverallStatus(Long documentId) {
        checkDocumentExists(documentId);
        List<Long> summaryIds = summaryRepository.getSummaryIdsByDocumentId(documentId);

        if (summaryIds.isEmpty()) {
            return LLMOverallStatusResult.of(documentId, NOT_REQUESTED, 0, 0);
        }

        List<TaskStatus> taskStatuses = getTaskStatuses(summaryIds);

        int totalPages = summaryIds.size();
        int completedPages = Collections.frequency(taskStatuses, COMPLETED);

        if (totalPages == completedPages) {
            return LLMOverallStatusResult.of(documentId, COMPLETED, totalPages, completedPages);
        }
        return LLMOverallStatusResult.of(documentId, IN_PROGRESS, totalPages, completedPages);
    }

    public LLMPageStatusResult fetchPageStatus(LLMPageStatusCommand command) { // TODO: 페이지 번호 검증 추가
        checkDocumentExists(command.documentId());
        Long summaryId =
                summaryRepository.getSummaryIdByDocumentIdAndPageNumber(command.documentId(), command.pageNumber());

        if (summaryId == null) {
            return LLMPageStatusResult.from(NOT_REQUESTED);
        }
        return LLMPageStatusResult.from(llmQueryRepository.getTaskStatusBySummaryId(summaryId));
    }

    public LLMAllPagesResult findAllPagesResult(Long documentId) {
        checkDocumentExists(documentId);

        List<SummaryPageContentResult> summaryResults =
                summaryRepository.getPageNumbersAndContentByDocumentId(documentId);
        List<ProblemPageContentResult> problemResults =
                problemRepository.getPageNumbersAndContentByDocumentId(documentId);

        checkSummaryAndProblemCountsEqual(summaryResults, problemResults);

        if (summaryResults.isEmpty()) {
            return LLMAllPagesResult.of(documentId, Collections.emptyList());
        }

        List<LLMResult> results = summaryResults.stream().map(summaryResult -> {
            LLMContent content = LLMContent.of(
                    summaryResult.content(),
                    findProblemContentByPageNumber(problemResults, summaryResult.pageNumber())
            );
            return LLMResult.of(summaryResult.pageNumber(), content);
        }).toList();

        return LLMAllPagesResult.of(documentId, results);
    }

    public LLMPageResult findPageResult(LLMPageResultCommand command) { // TODO: 페이지 번호 검증 추가
        checkDocumentExists(command.documentId());

        String summaryResult = summaryRepository.getSummaryContentByDocumentIdAndPageNumber(
                command.documentId(), command.pageNumber());
        String problemResult = problemRepository.getProblemContentByDocumentIdAndPageNumber(
                command.documentId(), command.pageNumber());

        checkSummaryAndProblemConsistency(command, summaryResult, problemResult);

        return LLMPageResult.of(summaryResult, problemResult);
    }

    private static void checkSummaryAndProblemConsistency(LLMPageResultCommand command, String summaryResult,
                                                          String problemResult) {
        if (summaryResult == null && problemResult != null) {
            log.error("요약과 문제 생성 결과가 매칭되지 않습니다. {} 페이지에 대한 요약 결과가 없습니다.", command.pageNumber());
            throw new InternalServerErrorException(LLM_TASK_RESULT_ERROR);
        }

        if (problemResult == null && summaryResult != null) {
            log.error("요약과 문제 생성 결과가 매칭되지 않습니다. {} 페이지에 대한 문제 생성 결과가 없습니다.", command.pageNumber());
            throw new InternalServerErrorException(LLM_TASK_RESULT_ERROR);
        }
    }

    private void checkDocumentExists(Long documentId) {
        if (!documentRepository.existsById(documentId)) {
            throw new NotFoundException(DOCUMENT_NOT_FOUND);
        }
    }

    private static void checkSummaryAndProblemCountsEqual(
            List<SummaryPageContentResult> summaryResults, List<ProblemPageContentResult> problemResults
    ) {
        if (summaryResults.size() != problemResults.size()) {
            log.error("요약 개수와 문제 개수가 일치하지 않습니다. 요약: {} 개, 문제: {} 개", summaryResults.size(), problemResults.size());
            throw new InternalServerErrorException(LLM_TASK_RESULT_ERROR);
        }
    }

    private List<TaskStatus> getTaskStatuses(List<Long> summaryIds) {
        return summaryIds.stream().map(llmQueryRepository::getTaskStatusBySummaryId).toList();
    }

    private String findProblemContentByPageNumber(List<ProblemPageContentResult> results, int pageNumber) {
        return results.stream()
                .filter(result -> result.pageNumber() == pageNumber)
                .findFirst()
                .map(ProblemPageContentResult::content)
                .orElseThrow(() -> {
                    log.error("요약과 문제 생성 결과가 매칭되지 않습니다. {} 페이지에 대한 문제 생성 결과가 없습니다.", pageNumber);
                    return new InternalServerErrorException(LLM_TASK_RESULT_ERROR);
                });
    }
}
