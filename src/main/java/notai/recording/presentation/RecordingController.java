package notai.recording.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notai.recording.application.RecordingService;
import notai.recording.application.command.RecordingSaveCommand;
import notai.recording.application.result.RecordingSaveResult;
import notai.recording.presentation.request.RecordingSaveRequest;
import notai.recording.presentation.response.RecordingSaveResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents/{documentId}/recordings")
@RequiredArgsConstructor
public class RecordingController {

    private final RecordingService recordingService;

    @PostMapping
    public ResponseEntity<RecordingSaveResponse> saveRecording(
            @PathVariable("documentId") Long documentId, @RequestBody @Valid RecordingSaveRequest request
    ) {
        RecordingSaveCommand command = request.toCommand(documentId);
        RecordingSaveResult result = recordingService.saveRecording(command);
        return ResponseEntity.status(CREATED).body(RecordingSaveResponse.from(result));
    }
}
