package notai.llm.application.command;

import java.util.UUID;

public record SummaryAndProblemUpdateCommand(
        UUID taskId,
        Integer pageNumber,
        String summary,
        String problem
) {
}
