package notai.llm.application.command;

public record LLMPageStatusCommand(
        Long documentId,
        Integer pageNumber
) {
    public static LLMPageStatusCommand of(Long documentId, Integer pageNumber) {
        return new LLMPageStatusCommand(documentId, pageNumber);
    }
}
