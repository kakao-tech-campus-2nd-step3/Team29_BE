package notai.annotation.application;

import lombok.RequiredArgsConstructor;
import notai.annotation.domain.Annotation;
import notai.annotation.domain.AnnotationRepository;
import notai.annotation.presentation.response.AnnotationResponse;
import notai.common.exception.type.NotFoundException;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnotationService {

    private final AnnotationRepository annotationRepository;
    private final DocumentRepository documentRepository;

    public Optional<Annotation> findById(Long annotationId) {
        return annotationRepository.findById(annotationId);
    }

    @Transactional
    public AnnotationResponse createAnnotation(Long documentId, int pageNumber, int x, int y, int width, int height, String content) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new NotFoundException("문서를 찾을 수 없습니다. ID: " + documentId));

        Annotation annotation = new Annotation(document, pageNumber, x, y, width, height, content);
        annotationRepository.save(annotation);
        return new AnnotationResponse(annotation);
    }

    @Transactional
    public AnnotationResponse updateAnnotation(Long documentId, Long annotationId, int x, int y, int width, int height, String content) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new NotFoundException("문서를 찾을 수 없습니다. ID: " + documentId));
        Annotation annotation = annotationRepository.findByIdAndDocumentId(annotationId, documentId)
                .orElseThrow(() -> new NotFoundException("주석을 찾을 수 없습니다. ID: " + annotationId));

        annotation.updateAnnotation(x, y, width, height, content);
        return new AnnotationResponse(annotation);
    }

    @Transactional
    public void deleteAnnotation(Long documentId, Long annotationId) {
        Annotation annotation = annotationRepository.findByIdAndDocumentId(annotationId, documentId)
                .orElseThrow(() -> new NotFoundException("주석을 찾을 수 없습니다. ID: " + annotationId));

        annotationRepository.delete(annotation);
    }
}
