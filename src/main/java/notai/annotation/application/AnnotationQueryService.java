package notai.annotation.application;

import lombok.RequiredArgsConstructor;
import notai.annotation.domain.Annotation;
import notai.annotation.domain.AnnotationRepository;
import notai.annotation.presentation.response.AnnotationResponse;
import notai.common.exception.type.NotFoundException;
import notai.document.domain.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnotationQueryService {

    private final AnnotationRepository annotationRepository;
    private final DocumentRepository documentRepository;

    @Transactional(readOnly = true)
    public List<AnnotationResponse> getAnnotationsByDocument(Long documentId) {
        documentRepository.findById(documentId)
                .orElseThrow(() -> new NotFoundException("문서를 찾을 수 없습니다. ID: " + documentId));

        List<Annotation> annotations = annotationRepository.findByDocumentId(documentId);
        if (annotations.isEmpty()) {
            throw new NotFoundException("해당 문서에 주석이 존재하지 않습니다.");
        }

        return annotations.stream()
                .map(AnnotationResponse::new)
                .collect(Collectors.toList());
    }
}
