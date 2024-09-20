package notai.common.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notai.auth.TokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final TokenService tokenService;
    private static final String AUTHENTICATION_TYPE = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = 7;

    public AuthInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null || !header.startsWith(AUTHENTICATION_TYPE)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String token = header.substring(BEARER_PREFIX_LENGTH);
        Long memberId = tokenService.extractMemberId(token);
        request.setAttribute("memberId", memberId);

        return true;
    }
}
