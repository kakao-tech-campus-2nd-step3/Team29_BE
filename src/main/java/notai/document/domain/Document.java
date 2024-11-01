package notai.document.domain;

import jakarta.persistence.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.validation.constraints.NotNull;
import static lombok.AccessLevel.PROTECTED;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notai.common.domain.RootEntity;
import notai.common.exception.ErrorMessages;
import static notai.common.exception.ErrorMessages.*;
import notai.common.exception.type.NotFoundException;
import notai.common.exception.type.UnAuthorizedException;
import notai.folder.domain.Folder;
import notai.member.domain.Member;

@Slf4j
@Entity
@Table(name = "document")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Document extends RootEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", referencedColumnName = "id")
    private Folder folder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @NotNull
    @Column(name = "name", length = 50)
    private String name;

    @NotNull
    @Column(name = "url")
    private String url;

    @NotNull
    @Column(name = "total_pages")
    private Integer totalPages;

    public Document(Folder folder, Member member, String name, String url, Integer totalPages) {
        this.member = member;
        this.folder = folder;
        this.name = name;
        this.url = url;
        this.totalPages = totalPages;
    }

    public Document(Member member, String name, String url, Integer totalPages) {
        this.member = member;
        this.name = name;
        this.url = url;
        this.totalPages = totalPages;
    }

    public void validateDocument(Long folderId) {
        if (!this.folder.getId().equals(folderId)) {
            throw new NotFoundException(DOCUMENT_NOT_FOUND);
        }
    }

    public void validatePageNumber(Integer pageNumber) {
        if (totalPages < pageNumber) {
            throw new NotFoundException(INVALID_DOCUMENT_PAGE);
        }
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void validateOwner(Member member) {
        if (!this.member.equals(member)) {
            throw new UnAuthorizedException(UNAUTHORIZED_DOCUMENT_ACCESS);
        }
    }
}
