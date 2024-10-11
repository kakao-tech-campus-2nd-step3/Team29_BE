package notai.ocr.domain;

import notai.common.exception.type.NotFoundException;
import notai.document.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static notai.common.exception.ErrorMessages.OCR_RESULT_NOT_FOUND;

public interface OCRRepository extends JpaRepository<OCR, Long> {
    default OCR getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(OCR_RESULT_NOT_FOUND));
    }

    List<OCR> findAllByDocumentId(Long documentId);

    void deleteAllByDocument(Document document);
}
