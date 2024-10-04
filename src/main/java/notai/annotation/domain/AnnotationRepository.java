package notai.annotation.domain;

import notai.common.exception.type.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnnotationRepository extends JpaRepository<Annotation, Long> {

    List<Annotation> findByDocumentIdAndPageNumberIn(Long documentId, List<Integer> pageNumbers);


    Optional<Annotation> findByIdAndDocumentId(Long id, Long documentId);

    default Annotation getById(Long annotationId) {
        return findById(annotationId)
                .orElseThrow(() -> new NotFoundException("주석을 찾을 수 없습니다. ID: " + annotationId));
    }
}
