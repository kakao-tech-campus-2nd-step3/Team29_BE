package notai.client.oauth.kakao;

import lombok.RequiredArgsConstructor;
import notai.client.oauth.OauthClient;
import notai.member.domain.Member;
import notai.member.domain.OauthProvider;
import static notai.member.domain.OauthProvider.KAKAO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoOauthClient implements OauthClient {

    private final KakaoClient kakaoClient;

    @Override
    public Member fetchMember(String accessToken) {
        return kakaoClient.fetchMember(accessToken).toDomain();
    }

    @Override
    public OauthProvider oauthProvider() {
        return KAKAO;
    }

}
