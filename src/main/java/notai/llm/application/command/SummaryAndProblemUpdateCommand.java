package notai.llm.application.command;

import java.util.UUID;

public record SummaryAndProblemUpdateCommand(
        UUID taskId,
        String summary,
        String problem
) {
}
