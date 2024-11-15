package notai.sttTask.presentation.response;

import notai.llm.domain.TaskStatus;
import notai.sttTask.application.result.SttTaskPageStatusResult;

public record SttTaskPageStatusResponse(
        TaskStatus status
) {
    public static SttTaskPageStatusResponse from(SttTaskPageStatusResult result) {
        return new SttTaskPageStatusResponse(result.status());
    }

}
