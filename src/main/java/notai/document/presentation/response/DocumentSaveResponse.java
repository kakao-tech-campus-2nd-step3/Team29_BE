package notai.document.presentation.response;

import notai.document.application.result.DocumentSaveResult;

public record DocumentSaveResponse(
        Long id,
        String name,
        String url
) {
    public static DocumentSaveResponse from(DocumentSaveResult documentSaveResult) {
        return new DocumentSaveResponse(documentSaveResult.id(), documentSaveResult.name(), documentSaveResult.url());
    }
}
