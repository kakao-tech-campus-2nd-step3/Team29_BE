package notai.llm.application.result;

import notai.llm.domain.TaskStatus;

public record LlmTaskOverallStatusResult(
        Long documentId,
        TaskStatus overallStatus,
        Integer totalPages,
        Integer completedPages
) {
    public static LlmTaskOverallStatusResult of(
            Long documentId, TaskStatus overallStatus, Integer totalPages, Integer completedPages
    ) {
        return new LlmTaskOverallStatusResult(documentId, overallStatus, totalPages, completedPages);
    }
}
