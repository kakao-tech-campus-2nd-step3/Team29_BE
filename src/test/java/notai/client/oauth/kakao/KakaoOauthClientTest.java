package notai.client.oauth.kakao;

import notai.member.domain.Member;
import notai.member.domain.OauthProvider;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

public class KakaoOauthClientTest {

    @Mock
    private KakaoClient kakaoClient;

    @InjectMocks
    private KakaoOauthClient kakaoOauthClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchMember() {
        long id = 12345L;
        String accessToken = "testAccessToken";
        String email = "email@example.com";
        boolean isEmailVerified = true;
        boolean isEmailValid = true;
        boolean emailNeedsAgreement = false;
        LocalDateTime connectedAt = LocalDateTime.now();
        boolean hasSignedUp = true;
        String nickname = "nickname";
        KakaoMemberResponse.Profile profile = new KakaoMemberResponse.Profile(nickname);

        KakaoMemberResponse.KakaoAccount kakaoAccount = new KakaoMemberResponse.KakaoAccount(profile,
                emailNeedsAgreement,
                isEmailValid,
                isEmailVerified,
                email
        );

        KakaoMemberResponse kakaoMemberResponse = new KakaoMemberResponse(id, hasSignedUp, connectedAt, kakaoAccount);

        when(kakaoClient.fetchMember(accessToken)).thenReturn(kakaoMemberResponse);

        Member member = kakaoOauthClient.fetchMember(accessToken);

        assertEquals(String.valueOf(id), member.getOauthId().getOauthId());
        assertEquals(OauthProvider.KAKAO, member.getOauthId().getOauthProvider());
        assertEquals(nickname, member.getNickname());
        assertEquals(email, member.getEmail());
    }
}
