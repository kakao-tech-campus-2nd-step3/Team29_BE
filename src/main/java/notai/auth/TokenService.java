package notai.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import notai.common.exception.type.UnAuthorizedException;
import notai.member.domain.Member;
import notai.member.domain.MemberRepository;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenService {
    private static final String MEMBER_ID_CLAIM = "memberId";

    private final SecretKey secretKey;
    private final long accessTokenExpirationMillis;
    private final long refreshTokenExpirationMillis;
    private final MemberRepository memberRepository;

    public TokenService(TokenProperty tokenProperty, MemberRepository memberRepository) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(tokenProperty.secretKey()));
        this.accessTokenExpirationMillis = tokenProperty.accessTokenExpirationMillis();
        this.refreshTokenExpirationMillis = tokenProperty.refreshTokenExpirationMillis();
        this.memberRepository = memberRepository;
    }

    public String createAccessToken(Long memberId) {
        return Jwts.builder()
                .claim(MEMBER_ID_CLAIM, memberId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationMillis))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    private String createRefreshToken() {
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMillis))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public TokenPair createTokenPair(Long memberId) {
        String accessToken = createAccessToken(memberId);
        String refreshToken = createRefreshToken();

        Member member = memberRepository.getById(memberId);
        member.updateRefreshToken(refreshToken);
        memberRepository.save(member);

        return new TokenPair(accessToken, refreshToken);
    }

    public TokenPair refreshTokenPair(String refreshToken) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new UnAuthorizedException("만료된 Refresh Token입니다.");
        } catch (Exception e) {
            throw new UnAuthorizedException("유효하지 않은 Refresh Token입니다.");
        }
        Member member = memberRepository.getByRefreshToken(refreshToken);

        return createTokenPair(member.getId());
    }

    public Long extractMemberId(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get(MEMBER_ID_CLAIM, Long.class);
        } catch (Exception e) {
            throw new UnAuthorizedException("유효하지 않은 토큰입니다.");
        }
    }
}
