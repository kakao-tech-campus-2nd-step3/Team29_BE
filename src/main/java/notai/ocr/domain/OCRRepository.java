package notai.ocr.domain;

import static notai.common.exception.ErrorMessages.OCR_RESULT_NOT_FOUND;
import notai.common.exception.type.NotFoundException;
import notai.document.domain.Document;
import notai.ocr.query.OCRQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OCRRepository extends JpaRepository<OCR, Long>, OCRQueryRepository {
    default OCR getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(OCR_RESULT_NOT_FOUND));
    }

    Optional<OCR> findOCRByDocumentIdAndPageNumber(Long documentId, Integer pageNumber);

    void deleteAllByDocument(Document document);
}
