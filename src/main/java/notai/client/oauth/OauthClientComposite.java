package notai.client.oauth;

import notai.common.exception.type.BadRequestException;
import notai.member.domain.Member;
import notai.member.domain.OauthProvider;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static notai.common.exception.ErrorMessages.INVALID_LOGIN_TYPE;

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
        return Optional.ofNullable(oauthClients.get(oauthProvider))
                       .orElseThrow(() -> new BadRequestException(INVALID_LOGIN_TYPE));
    }
}
