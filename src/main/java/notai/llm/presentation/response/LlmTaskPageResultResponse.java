package notai.llm.presentation.response;

import notai.llm.application.result.LlmTaskPageResult;

public record LlmTaskPageResultResponse(
        String summary,
        String problem
) {
    public static LlmTaskPageResultResponse from(LlmTaskPageResult result) {
        return new LlmTaskPageResultResponse(
                result.summary(),
                result.problem()
        );
    }
}
