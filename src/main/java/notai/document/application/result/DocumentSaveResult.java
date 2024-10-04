package notai.document.application.result;

public record DocumentSaveResult(
        Long id,
        String name,
        String url
) {
    public static DocumentSaveResult of(Long id, String name, String url) {
        return new DocumentSaveResult(id, name, url);
    }
}
