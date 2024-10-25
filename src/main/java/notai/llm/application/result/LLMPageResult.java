package notai.llm.application.result;

public record LLMPageResult(
        String summary,
        String problem
) {
    public static LLMPageResult of(String summary, String problem) {
        return new LLMPageResult(summary, problem);
    }
}
