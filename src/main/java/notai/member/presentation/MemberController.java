package notai.member.presentation;

import lombok.RequiredArgsConstructor;
import notai.auth.Auth;
import notai.auth.TokenPair;
import notai.auth.TokenService;
import notai.client.oauth.OauthClientComposite;
import notai.member.application.MemberQueryService;
import notai.member.application.MemberService;
import notai.member.domain.Member;
import notai.member.domain.OauthProvider;
import notai.member.presentation.request.OauthLoginRequest;
import notai.member.presentation.request.TokenRefreshRequest;
import notai.member.presentation.response.MemberFindResponse;
import notai.member.presentation.response.MemberOauthLoginResponse;
import notai.member.presentation.response.MemberTokenRefreshResponse;
import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members/")
public class MemberController {

    private final MemberService memberService;
    private final MemberQueryService memberQueryService;
    private final OauthClientComposite oauthClient;
    private final TokenService tokenService;

    @PostMapping("/oauth/login/{oauthProvider}")
    public ResponseEntity<MemberOauthLoginResponse> loginWithOauth(
            @PathVariable(value = "oauthProvider") OauthProvider oauthProvider, @RequestBody OauthLoginRequest request
    ) {
        Member member = oauthClient.fetchMember(oauthProvider, request.oauthAccessToken());
        Long memberId = memberService.login(member);
        TokenPair tokenPair = tokenService.createTokenPair(memberId);
        return ResponseEntity.status(CREATED).body(MemberOauthLoginResponse.from(tokenPair));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<MemberTokenRefreshResponse> refreshToken(
            @RequestBody TokenRefreshRequest request
    ) {
        TokenPair tokenPair = tokenService.refreshTokenPair(request.refreshToken());
        return ResponseEntity.status(CREATED).body(MemberTokenRefreshResponse.from(tokenPair));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberFindResponse> findMyProfile(
            @Auth Long memberId
    ) {
        return ResponseEntity.ok(MemberFindResponse.from(memberQueryService.findById(memberId)));
    }
}
