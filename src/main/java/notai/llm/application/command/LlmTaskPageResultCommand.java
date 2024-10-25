package notai.llm.application.command;

public record LlmTaskPageResultCommand(
        Long documentId,
        Integer pageNumber
) {
    public static LlmTaskPageResultCommand of(Long documentId, Integer pageNumber) {
        return new LlmTaskPageResultCommand(documentId, pageNumber);
    }
}
