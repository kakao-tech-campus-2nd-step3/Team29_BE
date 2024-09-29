package notai.folder.application.result;

public record FolderMoveResult(
        Long id,
        String name
) {
    public static FolderMoveResult of(Long id, String name) {
        return new FolderMoveResult(id, name);
    }
}
