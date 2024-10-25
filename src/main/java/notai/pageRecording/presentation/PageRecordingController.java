package notai.pageRecording.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import lombok.RequiredArgsConstructor;
import notai.pageRecording.application.PageRecordingService;
import notai.pageRecording.application.command.PageRecordingSaveCommand;
import notai.pageRecording.presentation.request.PageRecordingSaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.status(CREATED).build();
    }
}
