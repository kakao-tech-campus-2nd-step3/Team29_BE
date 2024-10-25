package notai.llm.presentation.response;

import notai.llm.application.result.LlmTaskSubmitResult;

import java.time.LocalDateTime;

public record LlmTaskSubmitResponse(
        Long documentId,
        LocalDateTime createdAt
) {
    public static LlmTaskSubmitResponse from(LlmTaskSubmitResult result) {
        return new LlmTaskSubmitResponse(result.documentId(), result.createdAt());
    }
}
