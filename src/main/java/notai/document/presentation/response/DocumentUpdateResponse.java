package notai.document.presentation.response;

import notai.document.application.result.DocumentUpdateResult;

public record DocumentUpdateResponse(
        Long id,
        String name,
        String url
) {
    public static DocumentUpdateResponse from(DocumentUpdateResult documentUpdateResult) {
        return new DocumentUpdateResponse(
                documentUpdateResult.id(),
                documentUpdateResult.name(),
                documentUpdateResult.url()
        );
    }
}
