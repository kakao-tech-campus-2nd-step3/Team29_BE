package notai.post.presentation.request;

public record PostSaveRequest(
        Long memberId,
        String title,
        String content
) {
}
