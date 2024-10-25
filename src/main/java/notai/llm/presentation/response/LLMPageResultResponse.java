package notai.llm.presentation.response;

import notai.llm.application.result.LLMPageResult;

public record LLMPageResultResponse(
        String summary,
        String problem
) {
    public static LLMPageResultResponse from(LLMPageResult result) {
        return new LLMPageResultResponse(
                result.summary(),
                result.problem()
        );
    }
}
