package notai.llm.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import notai.llm.application.command.SummaryAndProblemUpdateCommand;

import java.util.UUID;

public record SummaryAndProblemUpdateRequest(
        UUID taskId,

        @NotNull Long documentId,

        Integer totalPages,

        @NotNull @Positive Integer pageNumber,

        @NotBlank String summary,

        @NotBlank String problem
) {
    public SummaryAndProblemUpdateCommand toCommand() {
        return new SummaryAndProblemUpdateCommand(taskId, pageNumber, summary, problem);
    }
}
