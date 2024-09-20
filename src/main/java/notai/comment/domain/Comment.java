package notai.comment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @NotNull
    private BigInteger id;
    @NotNull
    private BigInteger member_id;
    @NotNull
    private BigInteger post_id;
    @NotNull
    private BigInteger parent_comment_id;
    @NotNull
    private String content;
    @NotNull
    private LocalDateTime created_at;
    @NotNull
    private LocalDateTime updated_at;

}
