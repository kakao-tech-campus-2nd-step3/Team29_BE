package notai.folder.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notai.auth.Auth;
import notai.folder.application.FolderQueryService;
import notai.folder.application.FolderService;
import notai.folder.application.result.FolderFindResult;
import notai.folder.application.result.FolderMoveResult;
import notai.folder.application.result.FolderSaveResult;
import notai.folder.presentation.request.FolderMoveRequest;
import notai.folder.presentation.request.FolderSaveRequest;
import notai.folder.presentation.response.FolderFindResponse;
import notai.folder.presentation.response.FolderMoveResponse;
import notai.folder.presentation.response.FolderSaveResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;
    private final FolderQueryService folderQueryService;

    @PostMapping
    public ResponseEntity<FolderSaveResponse> saveFolder(
            @Auth Long memberId, @Valid @RequestBody FolderSaveRequest folderSaveRequest
    ) {
        FolderSaveResult folderResult = saveFolderResult(memberId, folderSaveRequest);
        FolderSaveResponse response = FolderSaveResponse.from(folderResult);
        return ResponseEntity.created(URI.create("/api/folders/" + response.id())).body(response);
    }

    @PostMapping("/{id}/move")
    public ResponseEntity<FolderMoveResponse> moveFolder(
            @Auth Long memberId, @PathVariable Long id, @Valid @RequestBody FolderMoveRequest folderMoveRequest
    ) {
        FolderMoveResult folderResult = moveFolderWithRequest(memberId, id, folderMoveRequest);
        FolderMoveResponse response = FolderMoveResponse.from(folderResult);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FolderFindResponse>> getFolders(
            @Auth Long memberId, @RequestParam(required = false) Long parentFolderId
    ) {
        List<FolderFindResult> folderResults = folderQueryService.getFolders(memberId, parentFolderId);
        List<FolderFindResponse> response = folderResults.stream().map(FolderFindResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(
            @Auth Long memberId, @PathVariable Long id
    ) {
        folderService.deleteFolder(memberId, id);
        return ResponseEntity.noContent().build();
    }

    private FolderSaveResult saveFolderResult(Long memberId, FolderSaveRequest folderSaveRequest) {
        if (folderSaveRequest.parentFolderId() != null) {
            return folderService.saveSubFolder(memberId, folderSaveRequest);
        }
        return folderService.saveRootFolder(memberId, folderSaveRequest);
    }

    private FolderMoveResult moveFolderWithRequest(Long memberId, Long id, FolderMoveRequest folderMoveRequest) {
        if (folderMoveRequest.targetFolderId() != null) {
            return folderService.moveNewParentFolder(memberId, id, folderMoveRequest);
        }
        return folderService.moveRootFolder(memberId, id);
    }
}
