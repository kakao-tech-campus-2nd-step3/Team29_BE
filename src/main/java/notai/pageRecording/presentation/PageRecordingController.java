package notai.pageRecording.presentation;

import lombok.RequiredArgsConstructor;
import notai.pageRecording.application.PageRecordingService;
import notai.pageRecording.application.command.PageRecordingSaveCommand;
import notai.pageRecording.presentation.request.PageRecordingSaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents/{documentId}/recordings/page-turns")
@RequiredArgsConstructor
public class PageRecordingController {

    private final PageRecordingService pageRecordingService;

    @PostMapping
    public ResponseEntity<Void> savePageRecording(
            @PathVariable("documentId") Long documentId, @RequestBody PageRecordingSaveRequest request
    ) {
        PageRecordingSaveCommand command = request.toCommand(documentId);
        pageRecordingService.savePageRecording(command);
        return ResponseEntity.ok().build();
    }
}
