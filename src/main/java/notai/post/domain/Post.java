package notai.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.member.domain.Member;

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
    @ManyToOne
    @NotNull
    @JoinColumn(name="member_id")
    private Member member;
    @NotNull
    @Column(length = 255)
    private String title;
    @NotNull
    @Column(length = 255)
    private String contents;

    public Post(
            Member member,
            String title,
            String contents
    ) {
        this.member = member;
        this.title = title;
        this.contents = contents;
    }
}
