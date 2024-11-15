package notai.sttTask.presentation;

import lombok.RequiredArgsConstructor;
import notai.auth.Auth;
import notai.sttTask.application.SttTaskQueryService;
import notai.sttTask.application.command.SttTaskPageStatusCommand;
import notai.sttTask.application.result.SttTaskOverallStatusResult;
import notai.sttTask.application.result.SttTaskPageStatusResult;
import notai.sttTask.presentation.response.SttTaskOverallStatusResponse;
import notai.sttTask.presentation.response.SttTaskPageStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/ai/stt")
@RestController
public class SttTaskController {

    private final SttTaskQueryService sttTaskQueryService;

    @GetMapping("/status/{documentId}")
    public ResponseEntity<SttTaskOverallStatusResponse> fetchOverallStatus(
            @Auth Long memberId, @PathVariable("documentId") Long documentId
    ) {
        SttTaskOverallStatusResult result = sttTaskQueryService.fetchOverallStatus(memberId, documentId);
        return ResponseEntity.ok(SttTaskOverallStatusResponse.from(result));
    }

    @GetMapping("/status/{documentId}/{pageNumber}")
    public ResponseEntity<SttTaskPageStatusResponse> fetchPageStatus(
            @Auth Long memberId,
            @PathVariable("documentId") Long documentId,
            @PathVariable("pageNumber") Integer pageNumber
    ) {
        SttTaskPageStatusCommand command = SttTaskPageStatusCommand.of(documentId, pageNumber);
        SttTaskPageStatusResult result = sttTaskQueryService.fetchPageStatus(memberId, command);
        return ResponseEntity.ok(SttTaskPageStatusResponse.from(result));
    }
}
