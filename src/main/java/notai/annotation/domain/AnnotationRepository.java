package notai.annotation.domain;

import notai.common.exception.type.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static notai.common.exception.ErrorMessages.ANNOTATION_NOT_FOUND;

public interface AnnotationRepository extends JpaRepository<Annotation, Long> {

    List<Annotation> findByDocumentIdAndPageNumberIn(Long documentId, List<Integer> pageNumbers);


    Optional<Annotation> findByIdAndDocumentId(Long id, Long documentId);

    default Annotation getById(Long annotationId) {
        return findById(annotationId)
                .orElseThrow(() -> new NotFoundException(ANNOTATION_NOT_FOUND));
    }

    List<Annotation> findByDocumentIdAndPageNumber(Long documentId, Integer pageNumber);

    List<Annotation> findByDocumentId(Long documentId);
}
