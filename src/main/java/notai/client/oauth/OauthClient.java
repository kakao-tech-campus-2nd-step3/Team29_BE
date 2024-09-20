package notai.client.oauth;

import notai.member.domain.Member;
import notai.member.domain.OauthProvider;

public interface OauthClient {

	OauthProvider oauthProvider();

	Member fetchMember(String accessToken);
}
