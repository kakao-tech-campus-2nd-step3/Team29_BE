package notai.llm.application.result;

import notai.llm.domain.TaskStatus;

public record LLMStatusResult(
        Long documentId,
        TaskStatus overallStatus,
        Integer totalPages,
        Integer completedPages
) {
    public static LLMStatusResult of(
            Long documentId, TaskStatus overallStatus, Integer totalPages, Integer completedPages
    ) {
        return new LLMStatusResult(documentId, overallStatus, totalPages, completedPages);
    }
}
