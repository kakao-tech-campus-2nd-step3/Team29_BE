package notai.document.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;
import notai.folder.domain.Folder;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "document")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Document extends RootEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", referencedColumnName = "id")
    private Folder folder;

    @NotNull
    @Column(name = "name", length = 50)
    private String name;

    @NotNull
    @Column(name = "size")
    private Integer size;

    @NotNull
    @Column(name = "total_page")
    private Integer totalPage;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private DocumentStatus status;

    public Document(Folder folder, String name, Integer size, Integer totalPage, DocumentStatus status) {
        this.folder = folder;
        this.name = name;
        this.size = size;
        this.totalPage = totalPage;
        this.status = status;
    }
}
