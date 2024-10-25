package notai.llm.presentation.response;

import java.util.List;
import notai.llm.application.result.LLMAllPagesResult;
import notai.llm.application.result.LLMAllPagesResult.LLMContent;
import notai.llm.application.result.LLMAllPagesResult.LLMResult;

public record LLMAllPagesResultResponse(
        Long documentId,
        Integer totalPages,
        List<Result> results
) {
    public static LLMAllPagesResultResponse from(LLMAllPagesResult result) {
        return new LLMAllPagesResultResponse(
                result.documentId(),
                result.results().size(),
                result.results().stream().map(Result::from).toList()
        );
    }

    public record Result(
            Integer pageNumber,
            Content content
    ) {
        public static Result from(LLMResult result) {
            return new Result(result.pageNumber(), Content.from(result.content()));
        }
    }

    public record Content(
            String summary,
            String problem
    ) {
        public static Content from(LLMContent result) {
            return new Content(result.summary(), result.problem());
        }
    }
}
