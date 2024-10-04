package notai.recording.application.command;

public record RecordingSaveCommand(
        Long documentId,
        String audioData
) {
}
