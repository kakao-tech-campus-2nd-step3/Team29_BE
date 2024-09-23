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
    @ManyToOne
    @NotNull
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @NotNull
    @Column(name = "name", length = 50)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_folder_id", referencedColumnName = "id")
    private Folder parentFolder;

    public Folder(Member member, String name) {
        this.member = member;
        this.name = name;
    }

    public Folder(Member member, String name, Folder parentFolder) {
        this.member = member;
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
