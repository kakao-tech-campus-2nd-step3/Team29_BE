package notai.member.domain;

import jakarta.persistence.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.validation.constraints.NotNull;
import static lombok.AccessLevel.PROTECTED;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Member extends RootEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private OauthId oauthId;

    @NotNull
    @Column(length = 50)
    private String email;

    @NotNull
    @Column(length = 20)
    private String nickname;

    @NotNull
    @Column(length = 255)
    private String refreshToken;

    public Member(OauthId oauthId, String email, String nickname) {
        this.oauthId = oauthId;
        this.email = email;
        this.nickname = nickname;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
