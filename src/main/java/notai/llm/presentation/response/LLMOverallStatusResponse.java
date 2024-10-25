package notai.llm.presentation.response;

import notai.llm.application.result.LLMOverallStatusResult;
import notai.llm.domain.TaskStatus;

public record LLMOverallStatusResponse(
        Long documentId,
        TaskStatus overallStatus,
        Integer totalPages,
        Integer completedPages
) {
    public static LLMOverallStatusResponse from(LLMOverallStatusResult result) {
        return new LLMOverallStatusResponse(
                result.documentId(),
                result.overallStatus(),
                result.totalPages(),
                result.completedPages()
        );
    }
}
