package notai.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {
    @Id
    @NotNull
    private BigInteger id ;
    @NotNull
    private BigInteger member_id;
    @NotNull
    private String title;
    @NotNull
    private String contents;
    @NotNull
    private LocalDateTime created_at;
    @NotNull
    private LocalDateTime updated_at;

}
