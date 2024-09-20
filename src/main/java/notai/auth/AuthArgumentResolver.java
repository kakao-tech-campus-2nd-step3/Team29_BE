package notai.auth;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import notai.common.exception.type.UnAuthorizedException;
import notai.member.domain.MemberRepository;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class)
               && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Long resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = extractAccessToken(request);
        Long memberId = tokenService.extractMemberId(token);
        return memberRepository.getById(memberId).getId();
    }

    private static String extractAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new UnAuthorizedException("토큰 정보가 없어 인증에 실패했습니다.");
    }
}
