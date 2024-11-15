package notai.annotation.application;

import notai.annotation.domain.Annotation;
import notai.annotation.domain.AnnotationRepository;
import notai.annotation.presentation.response.AnnotationResponse;
import notai.common.exception.type.UnAuthorizedException;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import notai.folder.domain.Folder;
import notai.member.domain.Member;
import notai.member.domain.MemberRepository;
import notai.member.domain.OauthId;
import notai.member.domain.OauthProvider;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnnotationServiceTest {

    @InjectMocks
    private AnnotationService annotationService;

    @Mock
    private AnnotationRepository annotationRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void 주석_생성_성공() {
        // given
        Long memberId = 1L;
        Long documentId = 1L;
        int pageNumber = 1;
        int x = 10;
        int y = 20;
        int width = 100;
        int height = 50;
        String content = "Test Annotation";

        Member member = new Member(new OauthId("12345", OauthProvider.KAKAO), "test@example.com", "TestUser");
        Folder folder = new Folder(member, "TestFolder");
        Document document = new Document(folder, member, "TestDocument", "http://example.com/test.pdf", 10);
        Annotation annotation = new Annotation(document, pageNumber, x, y, width, height, content);

        given(memberRepository.getById(memberId)).willReturn(member);
        given(documentRepository.getById(documentId)).willReturn(document);
        given(annotationRepository.save(any(Annotation.class))).willReturn(annotation);

        // when
        AnnotationResponse response = annotationService.createAnnotation(
                memberId, documentId, pageNumber, x, y, width, height, content
        );

        // then
        assertAll(
                () -> verify(documentRepository).getById(documentId),
                () -> verify(memberRepository).getById(memberId),
                () -> verify(annotationRepository).save(any(Annotation.class)),
                () -> assertEquals(pageNumber, response.pageNumber()),
                () -> assertEquals(x, response.x()),
                () -> assertEquals(y, response.y()),
                () -> assertEquals(width, response.width()),
                () -> assertEquals(height, response.height()),
                () -> assertEquals(content, response.content())
        );
    }

    @Test
    void 주석_생성_실패_권한없음() {
        // given
        Long memberId = 1L;
        Long documentId = 1L;
        int pageNumber = 1;
        int x = 10;
        int y = 20;
        int width = 100;
        int height = 50;
        String content = "Test Annotation";

        Member owner = new Member(new OauthId("12345", OauthProvider.KAKAO), "owner@example.com", "Owner");
        Member otherMember = new Member(new OauthId("67890", OauthProvider.KAKAO), "other@example.com", "Other");
        Folder folder = new Folder(owner, "TestFolder");
        Document document = new Document(folder, owner, "TestDocument", "http://example.com/test.pdf", 10);

        given(memberRepository.getById(memberId)).willReturn(otherMember);
        given(documentRepository.getById(documentId)).willReturn(document);

        // when & then
        assertAll(
                () -> assertThrows(UnAuthorizedException.class, () -> 
                    annotationService.createAnnotation(memberId, documentId, pageNumber, x, y, width, height, content)
                ),
                () -> verify(documentRepository).getById(documentId),
                () -> verify(memberRepository).getById(memberId),
                () -> verify(annotationRepository, never()).save(any(Annotation.class))
        );
    }

    @Test
    void 주석_수정_성공() {
        // given
        Long memberId = 1L;
        Long documentId = 1L;
        Long annotationId = 1L;
        int newX = 15;
        int newY = 25;
        int newWidth = 150;
        int newHeight = 75;
        String newContent = "Updated Annotation";

        Member member = new Member(new OauthId("12345", OauthProvider.KAKAO), "test@example.com", "TestUser");
        Folder folder = new Folder(member, "TestFolder");
        Document document = new Document(folder, member, "TestDocument", "http://example.com/test.pdf", 10);
        Annotation annotation = new Annotation(document, 1, 10, 20, 100, 50, "Original Content");

        given(memberRepository.getById(memberId)).willReturn(member);
        given(documentRepository.getById(documentId)).willReturn(document);
        given(annotationRepository.getById(annotationId)).willReturn(annotation);

        // when
        AnnotationResponse response = annotationService.updateAnnotation(
                memberId, documentId, annotationId, newX, newY, newWidth, newHeight, newContent
        );

        // then
        assertAll(
                () -> verify(documentRepository).getById(documentId),
                () -> verify(memberRepository).getById(memberId),
                () -> verify(annotationRepository).getById(annotationId),
                () -> assertEquals(newX, response.x()),
                () -> assertEquals(newY, response.y()),
                () -> assertEquals(newWidth, response.width()),
                () -> assertEquals(newHeight, response.height()),
                () -> assertEquals(newContent, response.content())
        );
    }

    @Test
    void 주석_삭제_성공() {
        // given
        Long memberId = 1L;
        Long documentId = 1L;
        Long annotationId = 1L;

        Member member = new Member(new OauthId("12345", OauthProvider.KAKAO), "test@example.com", "TestUser");
        Folder folder = new Folder(member, "TestFolder");
        Document document = new Document(folder, member, "TestDocument", "http://example.com/test.pdf", 10);
        Annotation annotation = new Annotation(document, 1, 10, 20, 100, 50, "Test Content");

        given(memberRepository.getById(memberId)).willReturn(member);
        given(documentRepository.getById(documentId)).willReturn(document);
        given(annotationRepository.getById(annotationId)).willReturn(annotation);

        // when
        annotationService.deleteAnnotation(memberId, documentId, annotationId);

        // then
        assertAll(
                () -> verify(documentRepository).getById(documentId),
                () -> verify(memberRepository).getById(memberId),
                () -> verify(annotationRepository).getById(annotationId),
                () -> verify(annotationRepository).delete(annotation)
        );
    }
}
