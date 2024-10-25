package notai.llm.presentation.response;

import notai.llm.application.result.LLMPageStatusResult;
import notai.llm.domain.TaskStatus;

public record LLMPageStatusResponse(
        TaskStatus status
) {
    public static LLMPageStatusResponse from(LLMPageStatusResult result) {
        return new LLMPageStatusResponse(result.status());
    }
}
