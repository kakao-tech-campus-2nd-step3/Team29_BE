package notai.client.oauth;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import notai.common.exception.type.BadRequestException;
import notai.member.domain.Member;
import notai.member.domain.OauthProvider;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class OauthClientComposite {

    private final Map<OauthProvider, OauthClient> oauthClients;

    public OauthClientComposite(Set<OauthClient> oauthClients) {
        this.oauthClients = oauthClients.stream().collect(toMap(OauthClient::oauthProvider, identity()));
    }

    public Member fetchMember(OauthProvider oauthProvider, String accessToken) {
        return oauthClients.get(oauthProvider).fetchMember(accessToken);
    }

    public OauthClient getOauthClient(OauthProvider oauthProvider) {
        return Optional.ofNullable(oauthClients.get(oauthProvider)).orElseThrow(() -> new BadRequestException(
                "지원하지 않는 소셜 로그인 타입입니다."));
    }
}
