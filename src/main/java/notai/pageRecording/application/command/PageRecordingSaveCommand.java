package notai.pageRecording.application.command;

import java.util.List;

public record PageRecordingSaveCommand(
        Long documentId,
        Long recordingId,
        List<PageRecordingSession> sessions
) {
    public record PageRecordingSession(
            Integer pageNumber,
            Double startTime,
            Double endTime
    ) {
    }
}
