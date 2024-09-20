package notai.member.application;

import lombok.RequiredArgsConstructor;
import notai.member.application.result.MemberFindResult;
import notai.member.domain.MemberRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberFindResult findById(Long memberId) {
        return MemberFindResult.from(memberRepository.getById(memberId));
    }
}
