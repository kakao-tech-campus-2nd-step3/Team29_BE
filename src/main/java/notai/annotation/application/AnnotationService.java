package notai.annotation.application;

import lombok.RequiredArgsConstructor;
import notai.annotation.domain.Annotation;
import notai.annotation.domain.AnnotationRepository;
import notai.annotation.presentation.response.AnnotationResponse;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnnotationService {

    private final AnnotationRepository annotationRepository;
    private final DocumentRepository documentRepository;

    @Transactional
    public AnnotationResponse createAnnotation(Long documentId, int pageNumber, int x, int y, int width, int height, String content) {
        Document document = documentRepository.getById(documentId);

        Annotation annotation = new Annotation(document, pageNumber, x, y, width, height, content);
        Annotation savedAnnotation = annotationRepository.save(annotation);
        return AnnotationResponse.from(savedAnnotation);
    }

    @Transactional
    public AnnotationResponse updateAnnotation(Long documentId, Long annotationId, int x, int y, int width, int height, String content) {
        documentRepository.getById(documentId);
        Annotation annotation = annotationRepository.getById(annotationId);
        annotation.updateAnnotation(x, y, width, height, content);
        return AnnotationResponse.from(annotation);
    }

    @Transactional
    public void deleteAnnotation(Long documentId, Long annotationId) {
        documentRepository.getById(documentId);
        Annotation annotation = annotationRepository.getById(annotationId);
        annotationRepository.delete(annotation);
    }
}
