package notai.client.oauth.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import notai.member.domain.Member;
import notai.member.domain.OauthId;
import notai.member.domain.OauthProvider;

import java.time.LocalDateTime;

@JsonNaming(value = SnakeCaseStrategy.class)
public record KakaoMemberResponse(
		Long id,
		boolean hasSignedUp,
		LocalDateTime connectedAt,
		KakaoAccount kakaoAccount) {

	public Member toDomain() {
		return new Member(
				new OauthId(String.valueOf(id), OauthProvider.KAKAO),
				kakaoAccount.email,
				kakaoAccount.profile.nickname);
	}

	@JsonNaming(value = SnakeCaseStrategy.class)
	public record KakaoAccount(
			Profile profile,
			boolean emailNeedsAgreement,
			boolean isEmailValid,
			boolean isEmailVerified,
			String email) {
	}

	@JsonNaming(value = SnakeCaseStrategy.class)
	public record Profile(
			String nickname) {
	}
}
