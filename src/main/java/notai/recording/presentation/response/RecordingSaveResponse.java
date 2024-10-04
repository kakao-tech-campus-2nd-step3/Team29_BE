package notai.recording.presentation.response;

import notai.recording.application.result.RecordingSaveResult;

import java.time.LocalDateTime;

public record RecordingSaveResponse(
        Long recordingId,
        Long documentId,
        LocalDateTime createdAt
) {
    public static RecordingSaveResponse from(RecordingSaveResult result) {
        return new RecordingSaveResponse(result.recordingId(), result.documentId(), result.createdAt());
    }
}
