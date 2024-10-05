package notai.member.application.result;

import notai.member.domain.Member;

public record MemberFindResult(
        Long id, String nickname
) {
    public static MemberFindResult from(Member member) {
        return new MemberFindResult(member.getId(), member.getNickname());
    }
}
