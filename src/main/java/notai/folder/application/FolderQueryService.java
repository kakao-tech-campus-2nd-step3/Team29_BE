package notai.folder.application;

import lombok.RequiredArgsConstructor;
import notai.folder.application.result.FolderFindResult;
import notai.folder.domain.Folder;
import notai.folder.domain.FolderRepository;
import notai.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FolderQueryService {

    private final FolderRepository folderRepository;
    private static final Long ROOT_ID = -1L;

    public List<FolderFindResult> getFolders(Long memberId, Long folderId) {
        List<Folder> folders = getFoldersWithMemberAndParent(memberId, folderId);
        // document read
        return folders.stream().map(this::getFolderResult).toList();
    }

    private List<Folder> getFoldersWithMemberAndParent(Long memberId, Long folderId) {
        if (folderId == null || folderId.equals(ROOT_ID)) {
            return folderRepository.findAllByMemberIdAndParentFolderIsNull(memberId);
        }
        return folderRepository.findAllByMemberIdAndParentFolderId(memberId, folderId);
    }

    private FolderFindResult getFolderResult(Folder folder) {
        Long parentFolderId = folder.getParentFolder() != null ? folder.getParentFolder().getId() : null;
        return FolderFindResult.of(folder.getId(), parentFolderId, folder.getName());
    }
}
