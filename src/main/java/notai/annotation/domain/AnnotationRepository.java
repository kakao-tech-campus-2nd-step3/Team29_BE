package notai.annotation.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnnotationRepository extends JpaRepository<Annotation, Long> {

    List<Annotation> findByDocumentId(Long documentId);

    Optional<Annotation> findByIdAndDocumentId(Long id, Long documentId);
}
