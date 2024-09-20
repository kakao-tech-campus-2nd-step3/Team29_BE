package notai.comment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name="comment")
@Getter
@NoArgsConstructor(access= PROTECTED)
@AllArgsConstructor
public class Comment extends RootEntity<Long> {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable= false)
    private Long member_id;
    @Column(nullable= false)
    private Long post_id;
    @Column(nullable= false)
    private Long parent_comment_id;
    @Column(nullable= false)
    private String content;
    @Column(nullable= false)
    private LocalDateTime created_at;
    @Column(nullable= false)
    private LocalDateTime updated_at;

    public Comment(
            @Auth memberId,
            Long post_id,
            String content
            ) {
        this.member_id = memberId;
        this.post_id = post_id;
        this.content = content;
    }

}
