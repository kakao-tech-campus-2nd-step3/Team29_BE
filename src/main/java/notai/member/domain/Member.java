package notai.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notai.common.domain.RootEntity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

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

	@Column(length = 50, nullable = false)
	private String email;

	@Column(length = 20, nullable = true)
	private String nickname;

	@Column(length = 255, nullable = false)
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
