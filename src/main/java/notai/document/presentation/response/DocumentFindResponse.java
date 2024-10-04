package notai.document.presentation.response;

import notai.document.application.result.DocumentFindResult;

public record DocumentFindResponse(
        Long id,
        String name,
        String url
) {
    public static DocumentFindResponse from(DocumentFindResult documentFindResult) {
        return new DocumentFindResponse(documentFindResult.id(), documentFindResult.name(), documentFindResult.url());
    }
}
