package notai.sttTask.application.result;

import notai.llm.domain.TaskStatus;

public record SttTaskOverallStatusResult(
        Long documentId,
        TaskStatus overallStatus,
        int totalPages,
        int completedPages
) {
    public static SttTaskOverallStatusResult of(
            Long documentId,
            TaskStatus taskStatus,
            int totalPages,
            int completedPages
    ) {
        return new SttTaskOverallStatusResult(documentId, taskStatus, totalPages, completedPages);
    }
}
