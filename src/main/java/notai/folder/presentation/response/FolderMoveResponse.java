package notai.folder.presentation.response;

import notai.folder.application.result.FolderMoveResult;

public record FolderMoveResponse(
        Long id,
        String name
) {
    public static FolderMoveResponse from(FolderMoveResult folderMoveResult) {
        return new FolderMoveResponse(folderMoveResult.id(), folderMoveResult.name());
    }
}
