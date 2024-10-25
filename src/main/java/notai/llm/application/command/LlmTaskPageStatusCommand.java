package notai.llm.application.command;

public record LlmTaskPageStatusCommand(
        Long documentId,
        Integer pageNumber
) {
    public static LlmTaskPageStatusCommand of(Long documentId, Integer pageNumber) {
        return new LlmTaskPageStatusCommand(documentId, pageNumber);
    }
}
