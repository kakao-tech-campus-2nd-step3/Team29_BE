package notai.folder.application;

import lombok.RequiredArgsConstructor;
import notai.folder.application.result.FolderFindResult;
import notai.folder.domain.Folder;
import notai.folder.domain.FolderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderQueryService {

    private final FolderRepository folderRepository;

    public List<FolderFindResult> getFolders(Long memberId, Long parentFolderId) {
        List<Folder> folders = getFoldersWithMemberAndParent(memberId, parentFolderId);
        // document read
        return folders.stream().map(this::getFolderResult).toList();
    }

    private List<Folder> getFoldersWithMemberAndParent(Long memberId, Long parentFolderId) {
        if (parentFolderId == null) {
            return folderRepository.findAllByMemberIdAndParentFolderIsNull(memberId);
        }
        return folderRepository.findAllByMemberIdAndParentFolderId(memberId, parentFolderId);
    }

    private FolderFindResult getFolderResult(Folder folder) {
        Long parentFolderId = folder.getParentFolder() != null ? folder.getParentFolder().getId() : null;
        return FolderFindResult.of(folder.getId(), parentFolderId, folder.getName());
    }
}
