package notai.folder.presentation.response;

import notai.common.exception.ErrorMessages;
import notai.common.exception.type.BadRequestException;
import notai.document.presentation.response.DocumentFindResponse;

public record FindResponseWrapper(
        Object response,
        FolderAndDocumentResponseType folderAndDocumentResponseType

) {
    public static FindResponseWrapper fromFolderFindResponse(
            Object response
    ) {
        if (response instanceof FolderFindResponse) {
            return new FindResponseWrapper(response, FolderAndDocumentResponseType.FOLDER);
        }
        throw new BadRequestException(ErrorMessages.FOLDER_AND_DOCUMENT_INVALID_RESPONSE);
    }

    public static FindResponseWrapper fromDocumentFindResponse(
            Object response
    ) {
        if (response instanceof DocumentFindResponse) {
            return new FindResponseWrapper(response, FolderAndDocumentResponseType.DOCUMENT);
        }
        throw new BadRequestException(ErrorMessages.FOLDER_AND_DOCUMENT_INVALID_RESPONSE);
    }
}
