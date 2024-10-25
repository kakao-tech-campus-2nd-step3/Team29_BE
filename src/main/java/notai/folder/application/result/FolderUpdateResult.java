package notai.folder.application.result;

public record FolderUpdateResult(
        Long id,
        Long parentId,
        String name
) {
    public static FolderUpdateResult of(Long id, Long parentId, String name) {
        return new FolderUpdateResult(id, parentId, name);
    }
}
