package notai.ocr.query;

import notai.ocr.domain.OCR;

import java.util.List;

public interface OCRQueryRepository {
    List<OCR> findAllByDocumentIdAndPageNumber(Long documentId, Integer pageNumber);
}
