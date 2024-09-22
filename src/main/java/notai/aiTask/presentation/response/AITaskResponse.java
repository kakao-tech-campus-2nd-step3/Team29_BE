package notai.aiTask.presentation.response;

import java.time.LocalDateTime;

public record AITaskResponse(
    Long documentId,
    LocalDateTime createdAt
) {
    public static AITaskResponse of(Long documentId, LocalDateTime createdAt) {
        return new AITaskResponse(documentId, createdAt);
    }
}
