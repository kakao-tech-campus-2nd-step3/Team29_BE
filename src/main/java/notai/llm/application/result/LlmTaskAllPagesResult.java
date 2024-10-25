package notai.llm.application.result;

import java.util.List;

public record LlmTaskAllPagesResult(
        Long documentId,
        Integer totalPages,
        List<LlmResult> results
) {
    public static LlmTaskAllPagesResult of(Long documentId, List<LlmResult> results) {
        return new LlmTaskAllPagesResult(documentId, results.size(), results);
    }

    public record LlmResult(
            Integer pageNumber,
            LlmContent content
    ) {
        public static LlmResult of(Integer pageNumber, LlmContent content) {
            return new LlmResult(pageNumber, content);
        }
    }

    public record LlmContent(
            String summary,
            String problem
    ) {
        public static LlmContent of(String summary, String problem) {
            return new LlmContent(summary, problem);
        }
    }
}
