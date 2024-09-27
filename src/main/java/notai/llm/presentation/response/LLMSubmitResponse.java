package notai.llm.presentation.response;

import notai.llm.application.result.LLMSubmitResult;

import java.time.LocalDateTime;

public record LLMSubmitResponse(
        Long documentId,
        LocalDateTime createdAt
) {
    public static LLMSubmitResponse from(LLMSubmitResult result) {
        return new LLMSubmitResponse(result.documentId(), result.createdAt());
    }
}
