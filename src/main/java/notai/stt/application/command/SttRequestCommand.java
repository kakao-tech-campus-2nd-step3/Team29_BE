package notai.stt.application.command;

public record SttRequestCommand(
        Long recordingId,
        String audioFilePath
) {
}
