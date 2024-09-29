package notai.member.presentation.response;

import notai.auth.TokenPair;

public record MemberOauthLoginResponse(
        String accessToken,
        String refreshToken
) {
    public static MemberOauthLoginResponse from(TokenPair tokenPair) {
        return new MemberOauthLoginResponse(tokenPair.accessToken(), tokenPair.refreshToken());
    }
}
