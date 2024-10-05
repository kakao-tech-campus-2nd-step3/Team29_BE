package notai.llm.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notai.llm.application.LLMQueryService;
import notai.llm.application.LLMService;
import notai.llm.application.command.LLMSubmitCommand;
import notai.llm.application.command.SummaryAndProblemUpdateCommand;
import notai.llm.application.result.LLMResultsResult;
import notai.llm.application.result.LLMStatusResult;
import notai.llm.application.result.LLMSubmitResult;
import notai.llm.presentation.request.LLMSubmitRequest;
import notai.llm.presentation.request.SummaryAndProblemUpdateRequest;
import notai.llm.presentation.response.LLMResultsResponse;
import notai.llm.presentation.response.LLMStatusResponse;
import notai.llm.presentation.response.LLMSubmitResponse;
import notai.llm.presentation.response.SummaryAndProblemUpdateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/llm")
@RequiredArgsConstructor
public class LLMController {

    private final LLMService llmService;
    private final LLMQueryService llmQueryService;

    @PostMapping
    public ResponseEntity<LLMSubmitResponse> submitTask(@RequestBody @Valid LLMSubmitRequest request) {
        LLMSubmitCommand command = request.toCommand();
        LLMSubmitResult result = llmService.submitTask(command);
        return ResponseEntity.accepted().body(LLMSubmitResponse.from(result));
    }

    @GetMapping("/status/{documentId}")
    public ResponseEntity<LLMStatusResponse> fetchTaskStatus(@PathVariable("documentId") Long documentId) {
        LLMStatusResult result = llmQueryService.fetchTaskStatus(documentId);
        return ResponseEntity.ok(LLMStatusResponse.from(result));
    }

    @GetMapping("/results/{documentId}")
    public ResponseEntity<LLMResultsResponse> findTaskResult(@PathVariable("documentId") Long documentId) {
        LLMResultsResult result = llmQueryService.findTaskResult(documentId);
        return ResponseEntity.ok(LLMResultsResponse.from(result));
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
