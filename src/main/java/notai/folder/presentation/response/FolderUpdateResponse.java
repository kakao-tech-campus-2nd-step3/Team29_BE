package notai.folder.presentation.response;

import notai.folder.application.result.FolderUpdateResult;

public record FolderUpdateResponse(
        Long id,
        Long parentId,
        String name
) {
    public static FolderUpdateResponse from(FolderUpdateResult folderUpdateResult) {
        return new FolderUpdateResponse(
                folderUpdateResult.id(),
                folderUpdateResult.parentId(),
                folderUpdateResult.name()
        );
    }
}
