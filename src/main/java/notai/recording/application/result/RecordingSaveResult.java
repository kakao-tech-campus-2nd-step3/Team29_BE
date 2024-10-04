package notai.recording.application.result;

import java.time.LocalDateTime;

public record RecordingSaveResult(
        Long recordingId,
        Long documentId,
        LocalDateTime createdAt
) {
    public static RecordingSaveResult of(Long recordingId, Long documentId, LocalDateTime createdAt) {
        return new RecordingSaveResult(recordingId, documentId, createdAt);
    }
}
