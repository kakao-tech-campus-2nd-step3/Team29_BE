package notai.member.presentation.response;

import notai.auth.TokenPair;

public record MemberOauthLoginResopnse(
        String accessToken,
        String refreshToken
) {
    public static MemberOauthLoginResopnse from(TokenPair tokenPair) {
        return new MemberOauthLoginResopnse(tokenPair.accessToken(), tokenPair.refreshToken());
    }
}
