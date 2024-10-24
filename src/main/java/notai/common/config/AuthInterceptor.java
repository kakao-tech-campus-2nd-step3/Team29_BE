package notai.common.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notai.auth.TokenService;
import notai.common.exception.ErrorMessages;
import notai.common.exception.type.UnAuthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static notai.common.exception.ErrorMessages.*;
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
            throw new UnAuthorizedException(NOTFOUND_ACCESS_TOKEN);
        }

        String token = header.substring(BEARER_PREFIX_LENGTH);
        try {
            Long memberId = tokenService.extractMemberId(token);
            request.setAttribute("memberId", memberId);
        } catch (Exception e) {
            throw new UnAuthorizedException(INVALID_ACCESS_TOKEN);
        }
        return true;
    }
}
