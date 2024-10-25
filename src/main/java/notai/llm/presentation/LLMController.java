package notai.llm.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notai.llm.application.LLMQueryService;
import notai.llm.application.LLMService;
import notai.llm.application.command.LLMPageResultCommand;
import notai.llm.application.command.LLMPageStatusCommand;
import notai.llm.application.command.LLMSubmitCommand;
import notai.llm.application.command.SummaryAndProblemUpdateCommand;
import notai.llm.application.result.LLMAllPagesResult;
import notai.llm.application.result.LLMOverallStatusResult;
import notai.llm.application.result.LLMPageResult;
import notai.llm.application.result.LLMPageStatusResult;
import notai.llm.application.result.LLMSubmitResult;
import notai.llm.presentation.request.LLMSubmitRequest;
import notai.llm.presentation.request.SummaryAndProblemUpdateRequest;
import notai.llm.presentation.response.LLMAllPagesResultResponse;
import notai.llm.presentation.response.LLMOverallStatusResponse;
import notai.llm.presentation.response.LLMPageResultResponse;
import notai.llm.presentation.response.LLMPageStatusResponse;
import notai.llm.presentation.response.LLMSubmitResponse;
import notai.llm.presentation.response.SummaryAndProblemUpdateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/llm")
@RequiredArgsConstructor
public class LLMController {

    private final LLMService llmService;
    private final LLMQueryService llmQueryService;

    @PostMapping
    public ResponseEntity<LLMSubmitResponse> submitTask(@RequestBody @Valid LLMSubmitRequest request) {
        LLMSubmitCommand command = request.toCommand();
        LLMSubmitResult result = llmService.submitTasks(command);
        return ResponseEntity.accepted().body(LLMSubmitResponse.from(result));
    }

    @GetMapping("/status/{documentId}")
    public ResponseEntity<LLMOverallStatusResponse> fetchOverallStatus(@PathVariable("documentId") Long documentId) {
        LLMOverallStatusResult result = llmQueryService.fetchOverallStatus(documentId);
        return ResponseEntity.ok(LLMOverallStatusResponse.from(result));
    }

    @GetMapping("/status/{documentId}/{pageNumber}")
    public ResponseEntity<LLMPageStatusResponse> fetchPageStatus(
            @PathVariable("documentId") Long documentId, @PathVariable("pageNumber") Integer pageNumber
    ) {
        LLMPageStatusCommand command = LLMPageStatusCommand.of(documentId, pageNumber);
        LLMPageStatusResult result = llmQueryService.fetchPageStatus(command);
        return ResponseEntity.ok(LLMPageStatusResponse.from(result));
    }

    @GetMapping("/results/{documentId}")
    public ResponseEntity<LLMAllPagesResultResponse> findAllPagesResult(@PathVariable("documentId") Long documentId) {
        LLMAllPagesResult result = llmQueryService.findAllPagesResult(documentId);
        return ResponseEntity.ok(LLMAllPagesResultResponse.from(result));
    }

    @GetMapping("/results/{documentId}/{pageNumber}")
    public ResponseEntity<LLMPageResultResponse> findPageResult(
            @PathVariable("documentId") Long documentId, @PathVariable("pageNumber") Integer pageNumber) {
        LLMPageResultCommand command = LLMPageResultCommand.of(documentId, pageNumber);
        LLMPageResult result = llmQueryService.findPageResult(command);
        return ResponseEntity.ok(LLMPageResultResponse.from(result));
    }

    @PostMapping("/callback")
    public ResponseEntity<SummaryAndProblemUpdateResponse> handleTaskCallback(
            @RequestBody @Valid SummaryAndProblemUpdateRequest request
    ) {
        SummaryAndProblemUpdateCommand command = request.toCommand();
        Integer receivedPage = llmService.updateSummaryAndProblem(command);
        return ResponseEntity.ok(SummaryAndProblemUpdateResponse.from(receivedPage));
    }
}
