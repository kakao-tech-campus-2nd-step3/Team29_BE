package notai.llm.application.result;

import java.util.List;

public record LLMAllPagesResult(
        Long documentId,
        Integer totalPages,
        List<LLMResult> results
) {
    public static LLMAllPagesResult of(Long documentId, List<LLMResult> results) {
        return new LLMAllPagesResult(documentId, results.size(), results);
    }

    public record LLMResult(
            Integer pageNumber,
            LLMContent content
    ) {
        public static LLMResult of(Integer pageNumber, LLMContent content) {
            return new LLMResult(pageNumber, content);
        }
    }

    public record LLMContent(
            String summary,
            String problem
    ) {
        public static LLMContent of(String summary, String problem) {
            return new LLMContent(summary, problem);
        }
    }
}
