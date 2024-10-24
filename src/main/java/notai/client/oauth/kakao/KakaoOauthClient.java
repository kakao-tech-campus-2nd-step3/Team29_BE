package notai.client.oauth.kakao;

import lombok.RequiredArgsConstructor;
import notai.client.oauth.OauthClient;
import notai.member.domain.Member;
import notai.member.domain.OauthProvider;
import org.springframework.stereotype.Component;

import static notai.member.domain.OauthProvider.KAKAO;

@Component
@RequiredArgsConstructor
public class KakaoOauthClient implements OauthClient {

    private final KakaoClient kakaoClient;

    @Override
    public Member fetchMember(String accessToken) {
        return kakaoClient.fetchMember("Bearer " + accessToken).toDomain();
    }

    @Override
    public OauthProvider oauthProvider() {
        return KAKAO;
    }

}
