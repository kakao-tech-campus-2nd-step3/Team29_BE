package notai.post.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.member.domain.Member;

import static jakarta.persistence.FetchType.LAZY;
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

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Column(length = 255)
    private String title;

    @NotNull
    @Column(length = 255)
    private String contents;

    public Post(Member member, String title, String contents) {
        this.member = member;
        this.title = title;
        this.contents = contents;
    }
}
