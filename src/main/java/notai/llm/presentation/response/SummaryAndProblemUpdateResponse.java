package notai.llm.presentation.response;

public record SummaryAndProblemUpdateResponse(
        Integer receivedPage
) {
    public static SummaryAndProblemUpdateResponse from(Integer receivedPage) {
        return new SummaryAndProblemUpdateResponse(receivedPage);
    }
}
