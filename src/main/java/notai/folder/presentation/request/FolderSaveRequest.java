package notai.folder.presentation.request;

public record FolderSaveRequest(
        Long parentFolderId,
        String name
) {
}
