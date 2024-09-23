package notai.comment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Comment extends RootEntity<Long> {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long memberId;
    @Column(nullable = false)
    private Long postId;
    @Column(nullable = false)
    private Long parentCommentId;
    @Column(length = 255, nullable = false)
    private String content;
    public Comment(
            Long memberId,
            Long postId,
            String content
    ) {
        this.memberId = memberId;
        this.postId = postId;
        this.content = content;
    }

}
