package notai.recording.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import notai.recording.application.command.RecordingSaveCommand;

public record RecordingSaveRequest(
        @NotBlank String documentName,

        @Pattern(regexp = "data:audio/mpeg;base64,[a-zA-Z0-9+/=]+", message = "지원하지 않는 오디오 형식입니다.") String audioData
) {
    public RecordingSaveCommand toCommand(Long documentId) {
        return new RecordingSaveCommand(documentId, audioData);
    }
}
