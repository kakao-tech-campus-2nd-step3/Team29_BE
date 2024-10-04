package notai.document.application.result;

public record DocumentUpdateResult(
        Long id,
        String name,
        String url
) {
    public static DocumentUpdateResult of(Long id, String name, String url) {
        return new DocumentUpdateResult(id, name, url);
    }
}
