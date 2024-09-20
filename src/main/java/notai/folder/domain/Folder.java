package notai.folder.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;

@Entity
@Table(name = "folder")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Folder extends RootEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "member_id")
    private Long memberId;
    @NotNull
    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_folder_id", referencedColumnName = "id")
    private Folder parentFolder;

    public Folder(Long memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public Folder(Long memberId, String name, Folder parentFolder) {
        this.memberId = memberId;
        this.name = name;
        this.parentFolder = parentFolder;
    }

    public void moveRootFolder() {
        this.parentFolder = null;
    }

    public void moveNewParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }
}
