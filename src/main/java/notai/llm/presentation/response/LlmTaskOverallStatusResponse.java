package notai.llm.presentation.response;

import notai.llm.application.result.LlmTaskOverallStatusResult;
import notai.llm.domain.TaskStatus;

public record LlmTaskOverallStatusResponse(
        Long documentId,
        TaskStatus overallStatus,
        Integer totalPages,
        Integer completedPages
) {
    public static LlmTaskOverallStatusResponse from(LlmTaskOverallStatusResult result) {
        return new LlmTaskOverallStatusResponse(
                result.documentId(),
                result.overallStatus(),
                result.totalPages(),
                result.completedPages()
        );
    }
}
