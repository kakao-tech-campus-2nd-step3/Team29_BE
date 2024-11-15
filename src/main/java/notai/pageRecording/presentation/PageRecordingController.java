package notai.pageRecording.presentation;

import lombok.RequiredArgsConstructor;
import notai.auth.Auth;
import notai.member.domain.Member;
import notai.pageRecording.application.PageRecordingService;
import notai.pageRecording.application.command.PageRecordingSaveCommand;
import notai.pageRecording.presentation.request.PageRecordingSaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/documents/{documentId}/recordings/page-turns")
@RequiredArgsConstructor
public class PageRecordingController {

    private final PageRecordingService pageRecordingService;

    @PostMapping
    public ResponseEntity<Void> savePageRecording(
            @Auth Long memberId,
            @PathVariable("documentId") Long documentId,
            @RequestBody PageRecordingSaveRequest request
    ) {
        PageRecordingSaveCommand command = request.toCommand(documentId);
        pageRecordingService.savePageRecording(memberId, command);
        return ResponseEntity.status(CREATED).build();
    }
}
