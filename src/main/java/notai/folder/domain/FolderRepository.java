package notai.folder.domain;

import notai.common.exception.type.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static notai.common.exception.ErrorMessages.FOLDER_NOT_FOUND;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    default Folder getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(FOLDER_NOT_FOUND));
    }

    List<Folder> findAllByMemberIdAndParentFolderIsNull(Long memberId);

    List<Folder> findAllByMemberIdAndParentFolderId(Long memberId, Long parentFolderId);

    List<Folder> findAllByParentFolder(Folder parentFolder);
}
