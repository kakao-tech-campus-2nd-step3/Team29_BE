package notai.llm.presentation.response;

import notai.llm.application.result.LlmTaskPageStatusResult;
import notai.llm.domain.TaskStatus;

public record LlmTaskPageStatusResponse(
        TaskStatus status
) {
    public static LlmTaskPageStatusResponse from(LlmTaskPageStatusResult result) {
        return new LlmTaskPageStatusResponse(result.status());
    }
}
