package notai.document.domain;

import jakarta.persistence.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.validation.constraints.NotNull;
import static lombok.AccessLevel.PROTECTED;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notai.common.domain.RootEntity;
import static notai.common.exception.ErrorMessages.DOCUMENT_NOT_FOUND;
import static notai.common.exception.ErrorMessages.INVALID_DOCUMENT_PAGE;
import notai.common.exception.type.NotFoundException;
import notai.folder.domain.Folder;

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

    @NotNull
    @Column(name = "name", length = 50)
    private String name;

    @NotNull
    @Column(name = "url")
    private String url;

    @NotNull
    @Column(name = "total_pages")
    private Integer totalPages;

    public Document(Folder folder, String name, String url, Integer totalPages) {
        this.folder = folder;
        this.name = name;
        this.url = url;
        this.totalPages = totalPages;
    }

    public Document(String name, String url, Integer totalPages) {
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
}
