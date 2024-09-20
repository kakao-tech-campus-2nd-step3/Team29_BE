package notai.folder.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import notai.common.domain.RootEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folder")
@Getter
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentFolder", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Folder> childrenFolder = new ArrayList<>();

    protected Folder() {
    }

    public Folder(Long memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public Folder(Long memberId, String name, Folder parentFolder) {
        this.memberId = memberId;
        this.name = name;
        connectParentFolder(parentFolder);
    }

    public void moveRootFolder() {
        disconnectParentFolder();
        this.parentFolder = null;
    }

    public void moveNewParentFolder(Folder parentFolder) {
        disconnectParentFolder();
        connectParentFolder(parentFolder);
    }

    private void connectParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
        parentFolder.getChildrenFolder().add(this);
    }

    private void disconnectParentFolder() {
        if (this.parentFolder != null) {
            this.parentFolder.getChildrenFolder().remove(this);
        }
    }
}
