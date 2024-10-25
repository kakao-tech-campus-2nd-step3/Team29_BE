package notai.post.application.result;

import notai.post.domain.Post;

import java.time.LocalDateTime;

public record PostFindResult(
        Long id,
        Long memberId,
        String title,
        String contents,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
    public static PostFindResult of(
            Post post
    ) {
        return new PostFindResult(
                post.getId(),
                post.getMember().getId(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
