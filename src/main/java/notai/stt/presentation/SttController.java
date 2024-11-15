package notai.stt.presentation;

import lombok.RequiredArgsConstructor;
import notai.auth.Auth;
import notai.stt.application.SttQueryService;
import notai.stt.presentation.response.SttPageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stt")
@RequiredArgsConstructor
public class SttController {
    private final SttQueryService sttQueryService;

    @GetMapping("/documents/{documentId}/pages/{pageNumber}")
    public SttPageResponse getSttByPage(
            @Auth Long memberId,
            @PathVariable Long documentId,
            @PathVariable Integer pageNumber
    ) {
        return sttQueryService.getSttByPage(memberId, documentId, pageNumber);
    }
}
