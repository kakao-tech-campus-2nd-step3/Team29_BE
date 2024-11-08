package notai.stt.presentation.request;

import notai.stt.application.command.UpdateSttResultCommand;

import java.util.List;
import java.util.UUID;

public record SttCallbackRequest(
        String taskId,
        String text,
        List<Word> words
) {
    public UpdateSttResultCommand toCommand() {
        List<UpdateSttResultCommand.Word> commandWords = words().stream()
                .map(word -> new UpdateSttResultCommand.Word(
                        word.word(),
                        word.start(),
                        word.end()))
                .toList();
        return new UpdateSttResultCommand(UUID.fromString(taskId), commandWords);
    }
    
    public record Word(
            String word,
            double start,
            double end
    ) {
    }
}
