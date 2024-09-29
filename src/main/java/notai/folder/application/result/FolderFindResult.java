package notai.folder.application.result;

public record FolderFindResult(
        Long id,
        Long parentId,
        String name
) {
    public static FolderFindResult of(Long id, Long parentId, String name) {
        return new FolderFindResult(id, parentId, name);
    }
}
