package notai.ocr.domain;

import notai.common.exception.type.NotFoundException;
import notai.document.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OCRRepository extends JpaRepository<OCR, Long> {
    default OCR getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("OCR 데이터를 찾을 수 없습니다."));
    }

    List<OCR> findAllByDocumentId(Long documentId);

    void deleteAllByDocument(Document document);
}
