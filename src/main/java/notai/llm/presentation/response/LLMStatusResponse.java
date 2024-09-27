package notai.llm.presentation.response;

import notai.llm.application.result.LLMStatusResult;
import notai.llm.domain.TaskStatus;

public record LLMStatusResponse(
        Long documentId,
        TaskStatus overallStatus,
        Integer totalPages,
        Integer completedPages
) {
    public static LLMStatusResponse from(LLMStatusResult result) {
        return new LLMStatusResponse(
                result.documentId(),
                result.overallStatus(),
                result.totalPages(),
                result.completedPages()
        );
    }
}
