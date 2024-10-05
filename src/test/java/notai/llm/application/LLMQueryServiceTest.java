package notai.llm.application;

import notai.common.exception.type.InternalServerErrorException;
import notai.common.exception.type.NotFoundException;
import notai.document.domain.DocumentRepository;
import notai.llm.application.result.LLMResultsResult;
import notai.llm.application.result.LLMStatusResult;
import notai.llm.query.LLMQueryRepository;
import notai.problem.query.ProblemQueryRepository;
import notai.problem.query.result.ProblemPageContentResult;
import notai.summary.query.SummaryQueryRepository;
import notai.summary.query.result.SummaryPageContentResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static notai.llm.domain.TaskStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LLMQueryServiceTest {

    @InjectMocks
    private LLMQueryService llmQueryService;

    @Mock
    private LLMQueryRepository llmQueryRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private SummaryQueryRepository summaryQueryRepository;

    @Mock
    private ProblemQueryRepository problemQueryRepository;

    @Test
    void 작업_상태_확인시_존재하지_않는_문서ID로_요청한_경우_예외_발생() {
        // given
        given(documentRepository.existsById(anyLong())).willReturn(false);

        // when & then
        assertAll(() -> assertThrows(NotFoundException.class, () -> llmQueryService.fetchTaskStatus(1L)),
                () -> verify(documentRepository).existsById(anyLong())
        );
    }

    @Test
    void 작업_상태_확인시_모든_페이지의_작업이_완료된_경우_COMPLETED() {
        // given
        Long documentId = 1L;
        List<Long> summaryIds = List.of(1L, 2L, 3L);

        given(documentRepository.existsById(anyLong())).willReturn(true);
        given(summaryQueryRepository.getSummaryIdsByDocumentId(documentId)).willReturn(summaryIds);
        given(llmQueryRepository.getTaskStatusBySummaryId(1L)).willReturn(COMPLETED);
        given(llmQueryRepository.getTaskStatusBySummaryId(2L)).willReturn(COMPLETED);
        given(llmQueryRepository.getTaskStatusBySummaryId(3L)).willReturn(COMPLETED);

        // when
        LLMStatusResult result = llmQueryService.fetchTaskStatus(documentId);

        // then
        assertAll(() -> assertThat(result.overallStatus()).isEqualTo(COMPLETED),
                () -> assertThat(result.totalPages()).isEqualTo(3),
                () -> assertThat(result.completedPages()).isEqualTo(3),
                () -> verify(documentRepository).existsById(documentId),
                () -> verify(summaryQueryRepository).getSummaryIdsByDocumentId(documentId),
                () -> verify(llmQueryRepository).getTaskStatusBySummaryId(documentId)
        );
    }

    @Test
    void 작업_상태_확인시_모든_페이지의_작업이_완료되지_않은_경우_IN_PROGRESS() {
        // given
        Long documentId = 1L;
        List<Long> summaryIds = List.of(1L, 2L, 3L);

        given(documentRepository.existsById(anyLong())).willReturn(true);
        given(summaryQueryRepository.getSummaryIdsByDocumentId(documentId)).willReturn(summaryIds);
        given(llmQueryRepository.getTaskStatusBySummaryId(1L)).willReturn(COMPLETED);
        given(llmQueryRepository.getTaskStatusBySummaryId(2L)).willReturn(IN_PROGRESS);
        given(llmQueryRepository.getTaskStatusBySummaryId(3L)).willReturn(PENDING);

        // when
        LLMStatusResult result = llmQueryService.fetchTaskStatus(documentId);

        // then
        assertAll(() -> assertThat(result.overallStatus()).isEqualTo(IN_PROGRESS),
                () -> assertThat(result.totalPages()).isEqualTo(3),
                () -> assertThat(result.completedPages()).isEqualTo(1),
                () -> verify(documentRepository).existsById(documentId),
                () -> verify(summaryQueryRepository).getSummaryIdsByDocumentId(documentId),
                () -> verify(llmQueryRepository).getTaskStatusBySummaryId(documentId)
        );
    }

    @Test
    void 작업_결과_확인시_존재하지_않는_문서ID로_요청한_경우_예외_발생() {
        // given
        given(documentRepository.existsById(anyLong())).willReturn(false);

        // when & then
        assertAll(() -> assertThrows(NotFoundException.class, () -> llmQueryService.findTaskResult(1L)),
                () -> verify(documentRepository).existsById(anyLong())
        );
    }

    @Test
    void 작업_결과_확인시_생성된_요약과_문제의_수가_일치하지_않는_경우_예외_발생() {
        // given
        Long documentId = 1L;
        List<SummaryPageContentResult> summaryResults = List.of(new SummaryPageContentResult(1, "요약 내용"));
        List<ProblemPageContentResult> problemResults = List.of(new ProblemPageContentResult(1, "요약 내용"),
                new ProblemPageContentResult(2, "요약 내용")
        );

        given(documentRepository.existsById(anyLong())).willReturn(true);
        given(summaryQueryRepository.getPageNumbersAndContentByDocumentId(documentId)).willReturn(summaryResults);
        given(problemQueryRepository.getPageNumbersAndContentByDocumentId(documentId)).willReturn(problemResults);

        // when & then
        assertAll(() -> assertThrows(InternalServerErrorException.class, () -> llmQueryService.findTaskResult(1L)),
                () -> verify(documentRepository).existsById(documentId),
                () -> verify(summaryQueryRepository).getPageNumbersAndContentByDocumentId(documentId),
                () -> verify(problemQueryRepository).getPageNumbersAndContentByDocumentId(documentId)
        );
    }

    @Test
    void 작업_결과_확인() {
        // given
        Long documentId = 1L;
        List<SummaryPageContentResult> summaryResults = List.of(new SummaryPageContentResult(1, "요약 내용"),
                new SummaryPageContentResult(2, "요약 내용")
        );
        List<ProblemPageContentResult> problemResults = List.of(new ProblemPageContentResult(1, "요약 내용"),
                new ProblemPageContentResult(2, "요약 내용")
        );

        given(documentRepository.existsById(anyLong())).willReturn(true);
        given(summaryQueryRepository.getPageNumbersAndContentByDocumentId(documentId)).willReturn(summaryResults);
        given(problemQueryRepository.getPageNumbersAndContentByDocumentId(documentId)).willReturn(problemResults);

        // when
        LLMResultsResult response = llmQueryService.findTaskResult(documentId);

        // then
        assertAll(() -> assertEquals(documentId, response.documentId()),
                () -> assertEquals(2, response.results().size()),
                () -> verify(documentRepository).existsById(documentId),
                () -> verify(summaryQueryRepository).getPageNumbersAndContentByDocumentId(documentId),
                () -> verify(problemQueryRepository).getPageNumbersAndContentByDocumentId(documentId)
        );
    }
}