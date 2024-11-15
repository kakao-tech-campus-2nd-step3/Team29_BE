package notai.sttTask.presentation.response;

import notai.llm.domain.TaskStatus;
import notai.sttTask.application.result.SttTaskOverallStatusResult;

public record SttTaskOverallStatusResponse(
        Long documentId,
        TaskStatus overallStatus,
        Integer totalPages,
        Integer completedPages
) {
    public static SttTaskOverallStatusResponse from(SttTaskOverallStatusResult result) {
        return new SttTaskOverallStatusResponse(
                result.documentId(),
                result.overallStatus(),
                result.totalPages(),
                result.completedPages()
        );
    }
}
