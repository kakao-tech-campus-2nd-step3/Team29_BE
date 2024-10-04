package notai.folder.application;

import lombok.RequiredArgsConstructor;
import notai.document.application.DocumentService;
import notai.folder.application.result.FolderMoveResult;
import notai.folder.application.result.FolderSaveResult;
import notai.folder.domain.Folder;
import notai.folder.domain.FolderRepository;
import notai.folder.presentation.request.FolderMoveRequest;
import notai.folder.presentation.request.FolderSaveRequest;
import notai.member.domain.Member;
import notai.member.domain.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final MemberRepository memberRepository;
    private final DocumentService documentService;

    public FolderSaveResult saveRootFolder(Long memberId, FolderSaveRequest folderSaveRequest) {
        Member member = memberRepository.getById(memberId);
        Folder folder = new Folder(member, folderSaveRequest.name());
        Folder savedFolder = folderRepository.save(folder);
        return getFolderSaveResult(savedFolder);
    }

    public FolderSaveResult saveSubFolder(Long memberId, FolderSaveRequest folderSaveRequest) {
        Member member = memberRepository.getById(memberId);
        Folder parentFolder = folderRepository.getById(folderSaveRequest.parentFolderId());
        Folder folder = new Folder(member, folderSaveRequest.name(), parentFolder);
        Folder savedFolder = folderRepository.save(folder);
        return getFolderSaveResult(savedFolder);
    }

    public FolderMoveResult moveRootFolder(Long memberId, Long id) {
        Folder folder = folderRepository.getById(id);
        folder.validateOwner(memberId);
        folder.moveRootFolder();
        folderRepository.save(folder);
        return getFolderMoveResult(folder);
    }

    public FolderMoveResult moveNewParentFolder(Long memberId, Long id, FolderMoveRequest folderMoveRequest) {
        Folder folder = folderRepository.getById(id);
        Folder parentFolder = folderRepository.getById(folderMoveRequest.targetFolderId());
        folder.validateOwner(memberId);
        folder.moveNewParentFolder(parentFolder);
        folderRepository.save(folder);
        return getFolderMoveResult(folder);
    }

    public void deleteFolder(Long memberId, Long id) {
        Folder folder = folderRepository.getById(id);
        folder.validateOwner(memberId);
        List<Folder> subFolders = folderRepository.findAllByParentFolder(folder);
        for (Folder subFolder : subFolders) {
            deleteFolder(memberId, subFolder.getId());
        }
        documentService.deleteAllByFolder(folder);
        folderRepository.delete(folder);
    }

    private FolderSaveResult getFolderSaveResult(Folder folder) {
        Long parentFolderId = folder.getParentFolder() != null ? folder.getParentFolder().getId() : null;
        return FolderSaveResult.of(folder.getId(), parentFolderId, folder.getName());
    }

    private FolderMoveResult getFolderMoveResult(Folder folder) {
        return FolderMoveResult.of(folder.getId(), folder.getName());
    }
}
