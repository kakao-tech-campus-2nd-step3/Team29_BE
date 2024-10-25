package notai.llm.application.result;

import notai.llm.domain.TaskStatus;

public record LLMPageStatusResult(
        TaskStatus status
) {
    public static LLMPageStatusResult from(TaskStatus status) {
        return new LLMPageStatusResult(status);
    }
}
