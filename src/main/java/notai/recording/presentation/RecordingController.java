package notai.recording.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notai.auth.Auth;
import notai.member.domain.Member;
import notai.recording.application.RecordingService;
import notai.recording.application.command.RecordingSaveCommand;
import notai.recording.application.result.RecordingSaveResult;
import notai.recording.presentation.request.RecordingSaveRequest;
import notai.recording.presentation.response.RecordingSaveResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/documents/{documentId}/recordings")
@RequiredArgsConstructor
public class RecordingController {

    private final RecordingService recordingService;

    @PostMapping
    public ResponseEntity<RecordingSaveResponse> saveRecording(
            @Auth Member member,
            @PathVariable("documentId") Long documentId,
            @RequestBody @Valid RecordingSaveRequest request
    ) {
        RecordingSaveCommand command = request.toCommand(documentId);
        RecordingSaveResult result = recordingService.saveRecording(member, command);
        return ResponseEntity.status(CREATED).body(RecordingSaveResponse.from(result));
    }
}
