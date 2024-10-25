package notai.llm.application.command;

public record LLMPageResultCommand(
        Long documentId,
        Integer pageNumber
) {
    public static LLMPageResultCommand of(Long documentId, Integer pageNumber) {
        return new LLMPageResultCommand(documentId, pageNumber);
    }
}
