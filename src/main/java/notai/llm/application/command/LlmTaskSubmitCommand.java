package notai.llm.application.command;

import java.util.List;

public record LlmTaskSubmitCommand(
        Long documentId,
        List<Integer> pages
) {

}
