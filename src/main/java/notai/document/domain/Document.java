package notai.document.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notai.common.domain.RootEntity;
import notai.common.exception.type.NotFoundException;
import notai.folder.domain.Folder;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static notai.common.exception.ErrorMessages.DOCUMENT_NOT_FOUND;

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

    public Document(Folder folder, String name, String url) {
        this.folder = folder;
        this.name = name;
        this.url = url;
    }

    public Document(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public void validateDocument(Long folderId) {
        if (!this.folder.getId().equals(folderId)) {
            log.info("요청 폴더와 실제 문서를 소유한 폴더가 다릅니다.");
            throw new NotFoundException(DOCUMENT_NOT_FOUND);
        }
    }

    public void updateName(String name) {
        this.name = name;
    }
}
