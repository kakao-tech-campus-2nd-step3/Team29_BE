package notai.folder.application;

import lombok.RequiredArgsConstructor;
import notai.document.application.DocumentService;
import notai.folder.application.result.FolderMoveResult;
import notai.folder.application.result.FolderSaveResult;
import notai.folder.application.result.FolderUpdateResult;
import notai.folder.domain.Folder;
import notai.folder.domain.FolderRepository;
import notai.folder.presentation.request.FolderMoveRequest;
import notai.folder.presentation.request.FolderSaveRequest;
import notai.folder.presentation.request.FolderUpdateRequest;
import notai.member.domain.Member;
import notai.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FolderService {

    private final FolderRepository folderRepository;
    private final DocumentService documentService;
    private final MemberRepository memberRepository;

    public FolderSaveResult saveRootFolder(Long memberId, FolderSaveRequest folderSaveRequest) {
        Member member = memberRepository.getById(memberId);
        Folder folder = new Folder(member, folderSaveRequest.name());
        Folder savedFolder = folderRepository.save(folder);
        return getFolderSaveResult(savedFolder);
    }

    public FolderSaveResult saveSubFolder(Long memberId, FolderSaveRequest folderSaveRequest) {
        Folder parentFolder = folderRepository.getById(folderSaveRequest.parentFolderId());
        Member member = memberRepository.getById(memberId);
        Folder folder = new Folder(member, folderSaveRequest.name(), parentFolder);
        Folder savedFolder = folderRepository.save(folder);
        return getFolderSaveResult(savedFolder);
    }

    public FolderMoveResult moveRootFolder(Long memberId, Long id) {
        Folder folder = folderRepository.getById(id);
        Member member = memberRepository.getById(memberId);
        folder.validateOwner(member);
        folder.moveRootFolder();
        folderRepository.save(folder);
        return getFolderMoveResult(folder);
    }

    public FolderMoveResult moveNewParentFolder(Long memberId, Long id, FolderMoveRequest folderMoveRequest) {
        Folder folder = folderRepository.getById(id);
        Folder parentFolder = folderRepository.getById(folderMoveRequest.targetFolderId());
        Member member = memberRepository.getById(memberId);
        folder.validateOwner(member);
        folder.moveNewParentFolder(parentFolder);
        folderRepository.save(folder);
        return getFolderMoveResult(folder);
    }

    public FolderUpdateResult updateFolder(Long memberId, Long id, FolderUpdateRequest folderUpdateRequest) {
        Folder folder = folderRepository.getById(id);
        Member member = memberRepository.getById(memberId);
        folder.validateOwner(member);
        folder.updateName(folderUpdateRequest.name());
        folderRepository.save(folder);
        return getFolderUpdateResult(folder);
    }

    public void deleteFolder(Long memberId, Long id) {
        Folder folder = folderRepository.getById(id);
        Member member = memberRepository.getById(memberId);
        folder.validateOwner(member);
        List<Folder> subFolders = folderRepository.findAllByParentFolder(folder);
        for (Folder subFolder : subFolders) {
            deleteFolder(memberId, subFolder.getId());
        }
        documentService.deleteAllByFolder(memberId, folder);
        folderRepository.delete(folder);
    }

    private FolderSaveResult getFolderSaveResult(Folder folder) {
        Long parentFolderId = folder.getParentFolder() != null ? folder.getParentFolder().getId() : null;
        return FolderSaveResult.of(folder.getId(), parentFolderId, folder.getName());
    }

    private FolderMoveResult getFolderMoveResult(Folder folder) {
        return FolderMoveResult.of(folder.getId(), folder.getName());
    }

    private FolderUpdateResult getFolderUpdateResult(Folder folder) {
        Long parentFolderId = folder.getParentFolder() != null ? folder.getParentFolder().getId() : null;
        return FolderUpdateResult.of(folder.getId(), parentFolderId, folder.getName());
    }
}
