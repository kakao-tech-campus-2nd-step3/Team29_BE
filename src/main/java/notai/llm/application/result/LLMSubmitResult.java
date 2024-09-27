package notai.llm.application.result;

import java.time.LocalDateTime;

public record LLMSubmitResult(
        Long documentId,
        LocalDateTime createdAt
) {
    public static LLMSubmitResult of(Long documentId, LocalDateTime createdAt) {
        return new LLMSubmitResult(documentId, createdAt);
    }
}
