package notai.client.oauth.kakao;

import notai.member.domain.Member;
import notai.member.domain.OauthProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
        String accessToken = "testAccessToken";
        KakaoMemberResponse kakaoMemberResponse = new KakaoMemberResponse(
                12345L,
                true,
                null,
                new KakaoMemberResponse.KakaoAccount(
                        new KakaoMemberResponse.Profile("nickname"),
                        false,
                        true,
                        true,
                        "email@example.com"));

        when(kakaoClient.fetchMember(accessToken)).thenReturn(kakaoMemberResponse);

        Member member = kakaoOauthClient.fetchMember(accessToken);

        assertEquals("12345", member.getOauthId().getOauthId());
        assertEquals(OauthProvider.KAKAO, member.getOauthId().getOauthProvider());
        assertEquals("nickname", member.getNickname());
        assertEquals("email@example.com", member.getEmail());
    }
}
