package notai.annotation.application;

import lombok.RequiredArgsConstructor;
import notai.annotation.domain.Annotation;
import notai.annotation.domain.AnnotationRepository;
import notai.annotation.presentation.response.AnnotationResponse;
import notai.common.exception.type.NotFoundException;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import notai.member.domain.Member;
import notai.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static notai.common.exception.ErrorMessages.ANNOTATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AnnotationQueryService {

    private final AnnotationRepository annotationRepository;
    private final DocumentRepository documentRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<AnnotationResponse> getAnnotationsByDocumentAndPageNumbers(
            Long memberId, Long documentId, List<Integer> pageNumbers
    ) {
        Document document = documentRepository.getById(documentId);
        Member member = memberRepository.getById(memberId);
        document.validateOwner(member);

        List<Annotation> annotations = annotationRepository.findByDocumentIdAndPageNumberIn(documentId, pageNumbers);

        return annotations.stream()
                .map(AnnotationResponse::from)
                .toList();
    }
}
