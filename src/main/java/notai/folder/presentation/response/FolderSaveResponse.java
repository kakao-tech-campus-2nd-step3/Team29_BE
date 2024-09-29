package notai.folder.presentation.response;

import notai.folder.application.result.FolderSaveResult;

public record FolderSaveResponse(
        Long id,
        Long parentId,
        String name
) {
    public static FolderSaveResponse from(FolderSaveResult folderSaveResult) {
        return new FolderSaveResponse(folderSaveResult.id(), folderSaveResult.parentId(), folderSaveResult.name());
    }
}
