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
import notai.llm.application.command.LLMSubmitCommand;
import notai.llm.application.command.SummaryAndProblemUpdateCommand;
import notai.llm.application.result.LLMSubmitResult;
import notai.llm.domain.LLM;
import notai.llm.domain.LLMRepository;
import notai.member.domain.Member;
import notai.member.domain.OauthId;
import notai.member.domain.OauthProvider;
import notai.problem.domain.Problem;
import notai.problem.domain.ProblemRepository;
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

    @Mock
    private AnnotationRepository annotationRepository;

    @Mock
    private AiClient aiClient;

    @Test
    void AI_기능_요청시_존재하지_않는_문서ID로_요청한_경우_예외_발생() {
        // given
        Long documentId = 1L;
        List<Integer> pages = List.of(1, 2, 3);
        LLMSubmitCommand command = new LLMSubmitCommand(documentId, pages);

        given(documentRepository.getById(anyLong())).willThrow(NotFoundException.class);

        // when & then
        assertAll(() -> assertThrows(NotFoundException.class, () -> llmService.submitTask(command)),
                () -> verify(documentRepository, times(1)).getById(documentId),
                () -> verify(llmRepository, never()).save(any(LLM.class))
        );
    }

    @Test
    void AI_기능_요청_및_AI_클라이언트_테스트() {
        // given
        Long documentId = 1L;
        List<Integer> pages = List.of(1, 2);
        LLMSubmitCommand command = new LLMSubmitCommand(documentId, pages);

        Member member = new Member(new OauthId("12345", OauthProvider.KAKAO), "test@example.com", "TestUser");
        Folder folder = new Folder(member, "TestFolder");
        Document document = new Document(folder, "TestDocument", "http://example.com/test.pdf");

        List<Annotation> annotations = List.of(new Annotation(document, 1, 10, 20, 100, 50, "Annotation 1"),
                new Annotation(document, 1, 30, 40, 80, 60, "Annotation 2"),
                new Annotation(document, 2, 50, 60, 120, 70, "Annotation 3")
        );

        UUID taskId = UUID.randomUUID();
        TaskResponse taskResponse = new TaskResponse(taskId, "llm");

        given(documentRepository.getById(anyLong())).willReturn(document);
        given(annotationRepository.findByDocumentId(anyLong())).willReturn(annotations);
        given(aiClient.submitLlmTask(any(LlmTaskRequest.class))).willReturn(taskResponse);
        given(llmRepository.save(any(LLM.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        LLMSubmitResult result = llmService.submitTask(command);

        // then
        assertAll(() -> verify(documentRepository, times(1)).getById(documentId),
                () -> verify(annotationRepository, times(1)).findByDocumentId(documentId),
                () -> verify(aiClient, times(2)).submitLlmTask(any(LlmTaskRequest.class)),
                () -> verify(llmRepository, times(2)).save(any(LLM.class))
        );

        verify(aiClient).submitLlmTask(argThat(request -> request.keyboardNote().equals("Annotation 1, Annotation 2")));
        verify(aiClient).submitLlmTask(argThat(request -> request.keyboardNote().equals("Annotation 3")));
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
