package notai.folder.presentation.response;

import notai.folder.application.result.FolderFindResult;

public record FolderFindResponse(
        Long id,
        Long parentId,
        String name
) {
    public static FolderFindResponse from(FolderFindResult folderFindResult) {
        return new FolderFindResponse(folderFindResult.id(), folderFindResult.parentId(), folderFindResult.name());
    }
}
