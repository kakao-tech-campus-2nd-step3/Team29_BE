package notai.document.domain;

import notai.common.exception.type.NotFoundException;
import notai.document.query.DocumentQueryRepository;
import notai.folder.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static notai.common.exception.ErrorMessages.DOCUMENT_NOT_FOUND;

public interface DocumentRepository extends JpaRepository<Document, Long>, DocumentQueryRepository {
    default Document getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(DOCUMENT_NOT_FOUND));
    }

    List<Document> findAllByFolderId(Long folderId);

    void deleteAllByFolder(Folder folder);
}
