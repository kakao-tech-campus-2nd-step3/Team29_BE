package notai.llm.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import notai.llm.application.command.SummaryAndProblemUpdateCommand;

import java.util.UUID;

public record SummaryAndProblemUpdateRequest(
        @NotNull UUID taskId,

        @NotBlank String summary,

        @NotBlank String problem
) {
    public SummaryAndProblemUpdateCommand toCommand() {
        return new SummaryAndProblemUpdateCommand(taskId, summary, problem);
    }
}
