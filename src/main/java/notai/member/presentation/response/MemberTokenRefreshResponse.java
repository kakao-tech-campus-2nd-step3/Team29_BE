package notai.member.presentation.response;

import notai.auth.TokenPair;

public record MemberTokenRefreshResponse(
        String accessToken,
        String refreshToken
) {
    public static MemberTokenRefreshResponse from(TokenPair tokenPair) {
        return new MemberTokenRefreshResponse(tokenPair.accessToken(), tokenPair.refreshToken());
    }
}
