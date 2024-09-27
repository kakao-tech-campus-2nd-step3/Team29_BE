package notai.member.application;

import lombok.RequiredArgsConstructor;
import notai.member.domain.Member;
import notai.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Long login(Member member) {
        return memberRepository.findByOauthId(member.getOauthId())
                .orElseGet(() -> memberRepository.save(member))
                .getId()
                .longValue();
    }
}
