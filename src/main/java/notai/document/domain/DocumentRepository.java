package notai.document.domain;

import notai.document.query.DocumentQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long>, DocumentQueryRepository {
}
