package notai.aiTask.presentation.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import notai.aiTask.application.command.AITaskCommand;

public record AITaskRequest(

    @NotNull(message = "문서 ID는 필수 입력 값입니다.")
    Long documentId,

    List<@Positive(message = "페이지 번호는 양수여야 합니다.") Integer> pages
) {
    public AITaskCommand toCommand() {
        return new AITaskCommand(documentId, pages);
    }
}
