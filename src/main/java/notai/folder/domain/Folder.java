package notai.folder.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notai.common.domain.RootEntity;
import notai.common.exception.type.NotFoundException;
import notai.member.domain.Member;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static notai.common.exception.ErrorMessages.FOLDER_NOT_FOUND;

@Slf4j
@Entity
@Table(name = "folder")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Folder extends RootEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
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

    public void validateOwner(Long memberId) {
        if (!this.member.getId().equals(memberId)) {
            log.info("폴더 소유자가 요청 사용자와 다릅니다.");
            throw new NotFoundException(FOLDER_NOT_FOUND);
        }
    }
}
