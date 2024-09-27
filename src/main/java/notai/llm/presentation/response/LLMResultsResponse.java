package notai.llm.presentation.response;

import notai.llm.application.result.LLMResultsResult;
import notai.llm.application.result.LLMResultsResult.LLMContent;
import notai.llm.application.result.LLMResultsResult.LLMResult;

import java.util.List;

public record LLMResultsResponse(
        Long documentId,
        Integer totalPages,
        List<Result> results
) {
    public static LLMResultsResponse of(LLMResultsResult result) {
        return new LLMResultsResponse(
                result.documentId(),
                result.results().size(),
                result.results().stream().map(Result::of).toList()
        );
    }

    public record Result(
            Integer pageNumber,
            Content content
    ) {
        public static Result of(LLMResult result) {
            return new Result(result.pageNumber(), Content.of(result.content()));
        }
    }

    public record Content(
            String summary,
            String problem
    ) {
        public static Content of(LLMContent result) {
            return new Content(result.summary(), result.problem());
        }
    }
}
