package notai.llm.application.command;

import java.util.List;

public record LLMSubmitCommand(
        Long documentId,
        List<Integer> pages
) {

}
