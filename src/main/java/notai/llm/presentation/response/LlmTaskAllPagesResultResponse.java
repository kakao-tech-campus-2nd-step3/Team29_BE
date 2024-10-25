package notai.llm.presentation.response;

import notai.llm.application.result.LlmTaskAllPagesResult;
import notai.llm.application.result.LlmTaskAllPagesResult.LlmContent;
import notai.llm.application.result.LlmTaskAllPagesResult.LlmResult;

import java.util.List;

public record LlmTaskAllPagesResultResponse(
        Long documentId,
        Integer totalPages,
        List<Result> results
) {
    public static LlmTaskAllPagesResultResponse from(LlmTaskAllPagesResult result) {
        return new LlmTaskAllPagesResultResponse(
                result.documentId(),
                result.results().size(),
                result.results().stream().map(Result::from).toList()
        );
    }

    public record Result(
            Integer pageNumber,
            Content content
    ) {
        public static Result from(LlmResult result) {
            return new Result(result.pageNumber(), Content.from(result.content()));
        }
    }

    public record Content(
            String summary,
            String problem
    ) {
        public static Content from(LlmContent result) {
            return new Content(result.summary(), result.problem());
        }
    }
}
