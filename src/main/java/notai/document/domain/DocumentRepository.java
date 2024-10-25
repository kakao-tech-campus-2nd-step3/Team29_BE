package notai.document.domain;

import static notai.common.exception.ErrorMessages.DOCUMENT_NOT_FOUND;
import notai.common.exception.type.NotFoundException;
import notai.document.query.DocumentQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long>, DocumentQueryRepository {
    default Document getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(DOCUMENT_NOT_FOUND));
    }

    List<Document> findAllByFolderId(Long folderId);
}
