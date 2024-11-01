package notai.stt.application.command;

import java.util.List;
import java.util.UUID;

public record UpdateSttResultCommand(
        UUID taskId,
        List<Word> words
) {
    public record Word(
            String word,
            double start,
            double end
    ) {
    }
}
