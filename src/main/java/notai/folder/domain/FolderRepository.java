package notai.folder.domain;

import notai.common.exception.type.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    default Folder getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("폴더 정보를 찾을 수 없습니다."));
    }

    List<Folder> findAllByMemberIdAndParentFolderIsNull(Long memberId);

    List<Folder> findAllByMemberIdAndParentFolderId(Long memberId, Long parentFolderId);

    List<Folder> findAllByParentFolder(Folder parentFolder);
}
