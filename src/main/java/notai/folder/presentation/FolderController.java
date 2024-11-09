package notai.folder.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notai.auth.Auth;
import notai.folder.application.FolderQueryService;
import notai.folder.application.FolderService;
import notai.folder.application.result.FolderFindResult;
import notai.folder.application.result.FolderMoveResult;
import notai.folder.application.result.FolderSaveResult;
import notai.folder.application.result.FolderUpdateResult;
import notai.folder.presentation.request.FolderMoveRequest;
import notai.folder.presentation.request.FolderSaveRequest;
import notai.folder.presentation.request.FolderUpdateRequest;
import notai.folder.presentation.response.FolderFindResponse;
import notai.folder.presentation.response.FolderMoveResponse;
import notai.folder.presentation.response.FolderSaveResponse;
import notai.folder.presentation.response.FolderUpdateResponse;
import notai.member.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;
    private final FolderQueryService folderQueryService;

    @PostMapping
    public ResponseEntity<FolderSaveResponse> saveFolder(
            @Auth Member member, @Valid @RequestBody FolderSaveRequest folderSaveRequest
    ) {
        FolderSaveResult folderResult = saveFolderResult(member, folderSaveRequest);
        FolderSaveResponse response = FolderSaveResponse.from(folderResult);
        return ResponseEntity.created(URI.create("/api/folders/" + response.id())).body(response);
    }

    @PostMapping("/{id}/move")
    public ResponseEntity<FolderMoveResponse> moveFolder(
            @Auth Member member, @PathVariable Long id, @Valid @RequestBody FolderMoveRequest folderMoveRequest
    ) {
        FolderMoveResult folderResult = moveFolderWithRequest(member, id, folderMoveRequest);
        FolderMoveResponse response = FolderMoveResponse.from(folderResult);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FolderUpdateResponse> updateFolder(
            @Auth Member member, @PathVariable Long id, @Valid @RequestBody FolderUpdateRequest folderUpdateRequest
    ) {
        FolderUpdateResult folderResult = folderService.updateFolder(member, id, folderUpdateRequest);
        FolderUpdateResponse response = FolderUpdateResponse.from(folderResult);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FolderFindResponse>> getFolders(
            @Auth Member member, @RequestParam(required = false) Long parentFolderId
    ) {
        List<FolderFindResult> folderResults = folderQueryService.getFolders(member, parentFolderId);
        List<FolderFindResponse> response = folderResults.stream().map(FolderFindResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(
            @Auth Member member, @PathVariable Long id
    ) {
        folderService.deleteFolder(member, id);
        return ResponseEntity.noContent().build();
    }

    private FolderSaveResult saveFolderResult(Member member, FolderSaveRequest folderSaveRequest) {
        if (folderSaveRequest.parentFolderId() != null) {
            return folderService.saveSubFolder(member, folderSaveRequest);
        }
        return folderService.saveRootFolder(member, folderSaveRequest);
    }

    private FolderMoveResult moveFolderWithRequest(Member member, Long id, FolderMoveRequest folderMoveRequest) {
        if (folderMoveRequest.targetFolderId() != null) {
            return folderService.moveNewParentFolder(member, id, folderMoveRequest);
        }
        return folderService.moveRootFolder(member, id);
    }
}
