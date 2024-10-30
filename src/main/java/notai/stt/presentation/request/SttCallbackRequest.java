package notai.stt.presentation.request;

import notai.stt.application.command.UpdateSttResultCommand;
import notai.stt.application.command.UpdateSttResultCommand.Word;

import java.util.List;
import java.util.UUID;

public record SttCallbackRequest(
        String taskId,
        String state,
        SttResult result
) {
    public UpdateSttResultCommand toCommand() {
        List<Word> words = result.words().stream()
                .map(word -> new Word(
                        word.word(),
                        word.start(),
                        word.end()
                ))
                .toList();
        return new UpdateSttResultCommand(UUID.fromString(taskId), words);
    }

    public record SttResult(
            double audioLength,
            String language,
            double languageProbability,
            String text,
            List<Word> words
    ) {
        public record Word(
                double start,
                double end,
                String word
        ) {}
    }
}
