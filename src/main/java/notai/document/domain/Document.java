package notai.document.domain;

import jakarta.persistence.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.validation.constraints.NotNull;
import static lombok.AccessLevel.PROTECTED;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;
import notai.common.exception.type.NotFoundException;
import notai.folder.domain.Folder;

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
            throw new NotFoundException("해당 폴더 내에 존재하지 않는 자료입니다.");
        }
    }

    public void updateName(String name) {
        this.name = name;
    }
}
