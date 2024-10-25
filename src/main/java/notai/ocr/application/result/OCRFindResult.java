package notai.ocr.application.result;

public record OCRFindResult(
        Long documentId,
        Integer pageNumber,
        String result
) {
    public static OCRFindResult of(Long documentId, Integer pageNumber, String result) {
        return new OCRFindResult(documentId, pageNumber, result);
    }
}
