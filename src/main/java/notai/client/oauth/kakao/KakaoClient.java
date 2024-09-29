package notai.client.oauth.kakao;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;

public interface KakaoClient {

    @GetExchange(url = "https://kapi.kakao.com/v2/user/me")
    KakaoMemberResponse fetchMember(@RequestHeader(name = AUTHORIZATION) String accessToken);
}
