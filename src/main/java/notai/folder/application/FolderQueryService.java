package notai.folder.application;

import lombok.RequiredArgsConstructor;
import notai.folder.application.result.FolderFindResult;
import notai.folder.domain.Folder;
import notai.folder.domain.FolderRepository;
import notai.member.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderQueryService {

    private final FolderRepository folderRepository;

    public List<FolderFindResult> getFolders(Member member, Long parentFolderId) {
        List<Folder> folders = getFoldersWithMemberAndParent(member, parentFolderId);
        // document read
        return folders.stream().map(this::getFolderResult).toList();
    }

    private List<Folder> getFoldersWithMemberAndParent(Member member, Long parentFolderId) {
        if (parentFolderId == null) {
            return folderRepository.findAllByMemberIdAndParentFolderIsNull(member.getId());
        }
        return folderRepository.findAllByMemberIdAndParentFolderId(member.getId(), parentFolderId);
    }

    private FolderFindResult getFolderResult(Folder folder) {
        Long parentFolderId = folder.getParentFolder() != null ? folder.getParentFolder().getId() : null;
        return FolderFindResult.of(folder.getId(), parentFolderId, folder.getName());
    }
}
