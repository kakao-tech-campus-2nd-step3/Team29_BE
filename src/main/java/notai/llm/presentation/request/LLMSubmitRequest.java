package notai.llm.presentation.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import notai.llm.application.command.LLMSubmitCommand;

import java.util.List;

public record LLMSubmitRequest(

        @NotNull(message = "문서 ID는 필수 입력 값입니다.")
        Long documentId,

        List<@Positive(message = "페이지 번호는 양수여야 합니다.") Integer> pages
) {
    public LLMSubmitCommand toCommand() {
        return new LLMSubmitCommand(documentId, pages);
    }
}
