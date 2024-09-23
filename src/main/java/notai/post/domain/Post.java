package notai.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long memberId;
    @Column(length = 255, nullable = false)
    private String title;
    @Column(length = 255, nullable = false)
    private String contents;

    public Post(
            Long memberId,
            String title,
            String contents
    ) {
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
    }
}
