package notai.post.application.result;

public record PostSaveResult(
        Long id,
        String title
) {
    public static PostSaveResult of(Long id, String title) {
        return new PostSaveResult(id, title);
    }
}
