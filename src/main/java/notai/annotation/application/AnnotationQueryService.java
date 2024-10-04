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
    public List<AnnotationResponse> getAnnotationsByDocumentAndPageNumbers(Long documentId, List<Integer> pageNumbers) {
        documentRepository.getById(documentId);

        List<Annotation> annotations = annotationRepository.findByDocumentIdAndPageNumberIn(documentId, pageNumbers);
        if (annotations.isEmpty()) {
            throw new NotFoundException("해당 문서에 해당 페이지 번호의 주석이 존재하지 않습니다.");
        }

        return annotations.stream()
                .map(AnnotationResponse::from)
                .collect(Collectors.toList());
    }
}
