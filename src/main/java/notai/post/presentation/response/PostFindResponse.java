package notai.post.presentation.response;

import notai.post.application.result.PostFindResult;

import java.time.LocalDateTime;

public record PostFindResponse(
        Long id,
        Long memberId,
        String title,
        String contents,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostFindResponse from(PostFindResult postFindResult) {
        return new PostFindResponse(
                postFindResult.id(),
                postFindResult.memberId(),
                postFindResult.title(),
                postFindResult.contents(),
                postFindResult.createdAt(),
                postFindResult.updatedAt()
        );
    }
}
