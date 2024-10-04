package notai.document.application.result;

public record DocumentFindResult(
        Long id,
        String name,
        String url
) {
    public static DocumentFindResult of(Long id, String name, String url) {
        return new DocumentFindResult(id, name, url);
    }
}
