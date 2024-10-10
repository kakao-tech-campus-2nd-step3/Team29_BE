package notai.post.presentation.response;

import notai.post.application.result.PostSaveResult;

public record PostSaveResponse(
        Long id,
        String title
) {
    public static PostSaveResponse from(PostSaveResult postSaveResult) {
        return new PostSaveResponse(postSaveResult.id(), postSaveResult.title());
    }
}
