package notai.llm.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notai.common.exception.type.InternalServerErrorException;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import notai.llm.application.command.LlmTaskPageResultCommand;
import notai.llm.application.command.LlmTaskPageStatusCommand;
import notai.llm.application.result.LlmTaskAllPagesResult;
import notai.llm.application.result.LlmTaskAllPagesResult.LlmContent;
import notai.llm.application.result.LlmTaskAllPagesResult.LlmResult;
import notai.llm.application.result.LlmTaskOverallStatusResult;
import notai.llm.application.result.LlmTaskPageResult;
import notai.llm.application.result.LlmTaskPageStatusResult;
import notai.llm.domain.TaskStatus;
import notai.llm.query.LlmTaskQueryRepository;
import notai.member.domain.Member;
import notai.problem.domain.ProblemRepository;
import notai.problem.query.result.ProblemPageContentResult;
import notai.summary.domain.SummaryRepository;
import notai.summary.query.result.SummaryPageContentResult;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static notai.common.exception.ErrorMessages.LLM_TASK_RESULT_ERROR;
import static notai.llm.domain.TaskStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class LlmTaskQueryService {

    private final LlmTaskQueryRepository llmTaskQueryRepository;
    private final DocumentRepository documentRepository;
    private final SummaryRepository summaryRepository;
    private final ProblemRepository problemRepository;

    public LlmTaskOverallStatusResult fetchOverallStatus(Member member, Long documentId) {
        Document foundDocument = documentRepository.getById(documentId);
        foundDocument.validateOwner(member);

        List<Long> summaryIds = summaryRepository.getSummaryIdsByDocumentId(documentId);

        if (summaryIds.isEmpty()) {
            return LlmTaskOverallStatusResult.of(documentId, NOT_REQUESTED, 0, 0);
        }

        List<TaskStatus> taskStatuses = getTaskStatuses(summaryIds);

        int totalPages = summaryIds.size();
        int completedPages = Collections.frequency(taskStatuses, COMPLETED);

        if (totalPages == completedPages) {
            return LlmTaskOverallStatusResult.of(documentId, COMPLETED, totalPages, completedPages);
        }
        return LlmTaskOverallStatusResult.of(documentId, IN_PROGRESS, totalPages, completedPages);
    }

    public LlmTaskPageStatusResult fetchPageStatus(Member member, LlmTaskPageStatusCommand command) { // TODO: 페이지 번호 검증 추가
        Document foundDocument = documentRepository.getById(command.documentId());
        foundDocument.validateOwner(member);

        Long summaryId =
                summaryRepository.getSummaryIdByDocumentIdAndPageNumber(command.documentId(), command.pageNumber());

        if (summaryId == null) {
            return LlmTaskPageStatusResult.from(NOT_REQUESTED);
        }
        return LlmTaskPageStatusResult.from(llmTaskQueryRepository.getTaskStatusBySummaryId(summaryId));
    }

    public LlmTaskAllPagesResult findAllPagesResult(Member member, Long documentId) {
        Document foundDocument = documentRepository.getById(documentId);
        foundDocument.validateOwner(member);

        List<SummaryPageContentResult> summaryResults =
                summaryRepository.getPageNumbersAndContentByDocumentId(documentId);
        List<ProblemPageContentResult> problemResults =
                problemRepository.getPageNumbersAndContentByDocumentId(documentId);

        checkSummaryAndProblemCountsEqual(summaryResults, problemResults);

        if (summaryResults.isEmpty()) {
            return LlmTaskAllPagesResult.of(documentId, Collections.emptyList());
        }

        List<LlmResult> results = summaryResults.stream().map(summaryResult -> {
            LlmContent content = LlmContent.of(
                    summaryResult.content(),
                    findProblemContentByPageNumber(problemResults, summaryResult.pageNumber())
            );
            return LlmResult.of(summaryResult.pageNumber(), content);
        }).toList();

        return LlmTaskAllPagesResult.of(documentId, results);
    }

    public LlmTaskPageResult findPageResult(Member member, LlmTaskPageResultCommand command) { // TODO: 페이지 번호 검증 추가
        Document foundDocument = documentRepository.getById(command.documentId());
        foundDocument.validateOwner(member);

        String summaryResult = summaryRepository.getSummaryContentByDocumentIdAndPageNumber(
                command.documentId(), command.pageNumber());
        String problemResult = problemRepository.getProblemContentByDocumentIdAndPageNumber(
                command.documentId(), command.pageNumber());

        checkSummaryAndProblemConsistency(command, summaryResult, problemResult);

        return LlmTaskPageResult.of(summaryResult, problemResult);
    }

    private void checkSummaryAndProblemConsistency(
            LlmTaskPageResultCommand command, String summaryResult,
            String problemResult
    ) {
        if (summaryResult == null && problemResult != null) {
            log.error("요약과 문제 생성 결과가 매칭되지 않습니다. {} 페이지에 대한 요약 결과가 없습니다.", command.pageNumber());
            throw new InternalServerErrorException(LLM_TASK_RESULT_ERROR);
        }

        if (problemResult == null && summaryResult != null) {
            log.error("요약과 문제 생성 결과가 매칭되지 않습니다. {} 페이지에 대한 문제 생성 결과가 없습니다.", command.pageNumber());
            throw new InternalServerErrorException(LLM_TASK_RESULT_ERROR);
        }
    }

    private void checkSummaryAndProblemCountsEqual(
            List<SummaryPageContentResult> summaryResults, List<ProblemPageContentResult> problemResults
    ) {
        if (summaryResults.size() != problemResults.size()) {
            log.error("요약 개수와 문제 개수가 일치하지 않습니다. 요약: {} 개, 문제: {} 개", summaryResults.size(), problemResults.size());
            throw new InternalServerErrorException(LLM_TASK_RESULT_ERROR);
        }
    }

    private List<TaskStatus> getTaskStatuses(List<Long> summaryIds) {
        return summaryIds.stream().map(llmTaskQueryRepository::getTaskStatusBySummaryId).toList();
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
