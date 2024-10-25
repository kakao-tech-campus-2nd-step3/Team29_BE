package notai.llm.application.result;

public record LlmTaskPageResult(
        String summary,
        String problem
) {
    public static LlmTaskPageResult of(String summary, String problem) {
        return new LlmTaskPageResult(summary, problem);
    }
}
