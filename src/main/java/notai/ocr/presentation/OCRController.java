package notai.ocr.presentation;

import lombok.RequiredArgsConstructor;
import notai.auth.Auth;
import notai.member.domain.Member;
import notai.ocr.application.OCRQueryService;
import notai.ocr.application.result.OCRFindResult;
import notai.ocr.presentation.response.OCRFindResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents/{documentId}/ocrs")
@RequiredArgsConstructor
public class OCRController {

    private final OCRQueryService ocrQueryService;

    @GetMapping
    public ResponseEntity<OCRFindResponse> getDocuments(
            @Auth Member member, @PathVariable Long documentId, @RequestParam Integer pageNumber
    ) {
        OCRFindResult result = ocrQueryService.findOCR(member, documentId, pageNumber);
        OCRFindResponse response = OCRFindResponse.from(result);
        return ResponseEntity.ok(response);
    }
}

