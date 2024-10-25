package notai.post.presentation.request;

import notai.post.application.command.PostSaveCommand;

public record PostSaveRequest(
        Long memberId,
        String title,
        String content
) {
    public PostSaveCommand toCommand() {
        return new PostSaveCommand(memberId,title,content);
    }
}
