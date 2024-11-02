package notai.stt.presentation;

import lombok.RequiredArgsConstructor;
import notai.stt.application.SttService;
import notai.stt.presentation.request.SttCallbackRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SttCallbackController {

    private final SttService sttService;

    @PostMapping("/api/ai/stt/callback")
    public ResponseEntity<Void> sttCallback(@RequestBody SttCallbackRequest request) {
        sttService.updateSttResult(request.toCommand());
        return ResponseEntity.ok().build();
    }
}
