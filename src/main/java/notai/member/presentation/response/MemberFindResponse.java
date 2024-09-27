package notai.member.presentation.response;

import notai.member.application.result.MemberFindResult;

public record MemberFindResponse(
        Long id,
        String nickname
) {
    public static MemberFindResponse from(MemberFindResult result) {
        return new MemberFindResponse(
                result.id(),
                result.nickname()
        );
    }
}
