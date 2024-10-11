package notai.member.domain;

import notai.common.exception.type.NotFoundException;
import notai.common.exception.type.UnAuthorizedException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static notai.common.exception.ErrorMessages.INVALID_REFRESH_TOKEN;
import static notai.common.exception.ErrorMessages.MEMBER_NOT_FOUND;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthId(OauthId oauthId);

    default Member getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND));
    }

    Optional<Member> findByRefreshToken(String refreshToken);

    default Member getByRefreshToken(String refreshToken) {
        return findByRefreshToken(refreshToken).orElseThrow(() -> new UnAuthorizedException(INVALID_REFRESH_TOKEN));
    }
}
