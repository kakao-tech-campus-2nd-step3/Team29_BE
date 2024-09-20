package notai.member.domain;

import notai.common.exception.type.NotFoundException;
import notai.common.exception.type.UnAuthorizedException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthId(OauthId oauthId);

    default Member getById(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundException("회원 정보를 찾을 수 없습니다.")
        );
    }

    Optional<Member> findByRefreshToken(String refreshToken);

    default Member getByRefreshToken(String refreshToken) {
        return findByRefreshToken(refreshToken)
                .orElseThrow(() -> new UnAuthorizedException("유효하지 않은 Refresh Token입니다."));
    }
}
