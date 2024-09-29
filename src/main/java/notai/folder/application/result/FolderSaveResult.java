package notai.folder.application.result;

public record FolderSaveResult(
        Long id,
        Long parentId,
        String name
) {
    public static FolderSaveResult of(Long id, Long parentId, String name) {
        return new FolderSaveResult(id, parentId, name);
    }
}
