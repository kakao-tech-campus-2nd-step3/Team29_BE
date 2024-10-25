package notai.llm.application.result;

import notai.llm.domain.TaskStatus;

public record LlmTaskPageStatusResult(
        TaskStatus status
) {
    public static LlmTaskPageStatusResult from(TaskStatus status) {
        return new LlmTaskPageStatusResult(status);
    }
}
