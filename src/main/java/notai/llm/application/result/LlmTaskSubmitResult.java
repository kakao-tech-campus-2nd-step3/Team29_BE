package notai.llm.application.result;

import java.time.LocalDateTime;

public record LlmTaskSubmitResult(
        Long documentId,
        LocalDateTime createdAt
) {
    public static LlmTaskSubmitResult of(Long documentId, LocalDateTime createdAt) {
        return new LlmTaskSubmitResult(documentId, createdAt);
    }
}
