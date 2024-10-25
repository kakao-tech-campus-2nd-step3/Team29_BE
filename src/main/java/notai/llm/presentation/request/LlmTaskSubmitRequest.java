package notai.llm.presentation.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import notai.llm.application.command.LlmTaskSubmitCommand;

import java.util.List;

public record LlmTaskSubmitRequest(

        @NotNull(message = "문서 ID는 필수 입력 값입니다.") Long documentId,

        List<@Positive(message = "페이지 번호는 양수여야 합니다.") Integer> pages
) {
    public LlmTaskSubmitCommand toCommand() {
        return new LlmTaskSubmitCommand(documentId, pages);
    }
}
