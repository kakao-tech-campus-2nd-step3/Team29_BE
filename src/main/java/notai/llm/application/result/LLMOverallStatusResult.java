package notai.llm.application.result;

import notai.llm.domain.TaskStatus;

public record LLMOverallStatusResult(
        Long documentId,
        TaskStatus overallStatus,
        Integer totalPages,
        Integer completedPages
) {
    public static LLMOverallStatusResult of(
            Long documentId, TaskStatus overallStatus, Integer totalPages, Integer completedPages
    ) {
        return new LLMOverallStatusResult(documentId, overallStatus, totalPages, completedPages);
    }
}
