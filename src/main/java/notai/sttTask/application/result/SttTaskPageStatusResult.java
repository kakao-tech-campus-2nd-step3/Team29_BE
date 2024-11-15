package notai.sttTask.application.result;

import notai.llm.domain.TaskStatus;

public record SttTaskPageStatusResult(
        Integer pageNumber,
        TaskStatus status
) {
    public static SttTaskPageStatusResult of(Integer pageNumber, TaskStatus status) {
        return new SttTaskPageStatusResult(pageNumber, status);
    }
}
