package notai.ocr.presentation.response;

import notai.ocr.application.result.OCRFindResult;

public record OCRFindResponse(
        Long documentId,
        Integer pageNumber,
        String result
) {
    public static OCRFindResponse from(
            OCRFindResult ocrFindResult
    ) {
        return new OCRFindResponse(ocrFindResult.documentId(), ocrFindResult.pageNumber(), ocrFindResult.result());
    }
}
