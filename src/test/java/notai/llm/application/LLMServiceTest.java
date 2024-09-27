package notai.llm.application;

import notai.common.exception.type.NotFoundException;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import notai.llm.application.command.LLMSubmitCommand;
import notai.llm.application.command.SummaryAndProblemUpdateCommand;
import notai.llm.application.result.LLMSubmitResult;
import notai.llm.domain.LLM;
import notai.llm.domain.LLMRepository;
import notai.problem.domain.Problem;
import notai.problem.domain.ProblemRepository;
import notai.summary.domain.Summary;
import notai.summary.domain.SummaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LLMServiceTest {

    @InjectMocks
    private LLMService llmService;

    @Mock
    private LLMRepository llmRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private SummaryRepository summaryRepository;

    @Mock
    private ProblemRepository problemRepository;

    @Test
    void AI_기능_요청시_존재하지_않는_문서ID로_요청한_경우_예외_발생() {
        // given
        Long documentId = 1L;
        List<Integer> pages = List.of(1, 2, 3);
        LLMSubmitCommand command = new LLMSubmitCommand(documentId, pages);

        given(documentRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertAll(() -> assertThrows(NotFoundException.class, () -> llmService.submitTask(command)),
                () -> verify(documentRepository, times(1)).findById(documentId),
                () -> verify(llmRepository, never()).save(any(LLM.class))
        );
    }

    @Test
    void AI_기능_요청() {
        // given
        Long documentId = 1L;
        List<Integer> pages = List.of(1, 2, 3);
        LLMSubmitCommand command = new LLMSubmitCommand(documentId, pages);
        Document document = mock(Document.class);

        given(documentRepository.findById(anyLong())).willReturn(Optional.of(document));
        given(llmRepository.save(any(LLM.class))).willAnswer(invocation -> invocation.getArgument(0));
        // when
        LLMSubmitResult result = llmService.submitTask(command);

        // then
        assertAll(() -> verify(documentRepository, times(1)).findById(anyLong()),
                () -> verify(llmRepository, times(3)).save(any(LLM.class))
        );
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

        LLM taskRecord = mock(LLM.class);
        Summary summary = mock(Summary.class);
        Problem problem = mock(Problem.class);

        SummaryAndProblemUpdateCommand command = new SummaryAndProblemUpdateCommand(taskId,
                pageNumber,
                summaryContent,
                problemContent
        );

        given(llmRepository.getById(any(UUID.class))).willReturn(taskRecord);
        given(summaryRepository.getById(anyLong())).willReturn(summary);
        given(problemRepository.getById(anyLong())).willReturn(problem);

        given(taskRecord.getSummary()).willReturn(summary);
        given(taskRecord.getProblem()).willReturn(problem);
        given(summary.getId()).willReturn(summaryId);
        given(problem.getId()).willReturn(problemId);

        // when
        Integer resultPageNumber = llmService.updateSummaryAndProblem(command);

        // then
        assertAll(() -> verify(taskRecord).completeTask(),
                () -> verify(summary).updateContent(summaryContent),
                () -> verify(problem).updateContent(problemContent),
                () -> verify(llmRepository, times(1)).save(taskRecord),
                () -> verify(summaryRepository, times(1)).save(summary),
                () -> verify(problemRepository, times(1)).save(problem),
                () -> assertEquals(pageNumber, resultPageNumber)
        );
    }
}