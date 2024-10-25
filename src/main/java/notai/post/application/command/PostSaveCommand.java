package notai.post.application.command;

public record PostSaveCommand(
        Long memberId,
        String title,
        String content
) {
}
