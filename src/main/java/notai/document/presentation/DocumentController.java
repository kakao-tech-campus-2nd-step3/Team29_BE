package notai.document.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import notai.auth.Auth;
import notai.document.application.DocumentQueryService;
import notai.document.application.DocumentService;
import notai.document.application.result.DocumentFindResult;
import notai.document.application.result.DocumentSaveResult;
import notai.document.application.result.DocumentUpdateResult;
import notai.document.presentation.request.DocumentSaveRequest;
import notai.document.presentation.request.DocumentUpdateRequest;
import notai.document.presentation.response.DocumentFindResponse;
import notai.document.presentation.response.DocumentSaveResponse;
import notai.document.presentation.response.DocumentUpdateResponse;
import notai.member.domain.Member;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/folders/{folderId}/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentQueryService documentQueryService;

    private static final Long ROOT_FOLDER_ID = -1L;
    private static final String FOLDER_URL_FORMAT = "/api/folders/%s/documents/%s";

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentSaveResponse> saveDocument(
            @Auth Member member,
            @PathVariable Long folderId,
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_PDF_VALUE))
            @RequestPart MultipartFile pdfFile,
            @RequestPart DocumentSaveRequest documentSaveRequest
    ) {

        DocumentSaveResult documentSaveResult;
        if (folderId.equals(ROOT_FOLDER_ID)) {
            documentSaveResult = documentService.saveRootDocument(member, pdfFile, documentSaveRequest);
        } else {
            documentSaveResult = documentService.saveDocument(member, folderId, pdfFile, documentSaveRequest);
        }
        DocumentSaveResponse response = DocumentSaveResponse.from(documentSaveResult);
        String url = String.format(FOLDER_URL_FORMAT, folderId, response.id());
        return ResponseEntity.created(URI.create(url)).body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DocumentUpdateResponse> updateDocument(
            @Auth Member member,
            @PathVariable Long folderId,
            @PathVariable Long id,
            @RequestBody DocumentUpdateRequest documentUpdateRequest
    ) {
        DocumentUpdateResult documentUpdateResult = documentService.updateDocument(
                member,
                folderId,
                id,
                documentUpdateRequest
        );
        DocumentUpdateResponse response = DocumentUpdateResponse.from(documentUpdateResult);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DocumentFindResponse>> getDocuments(
            @Auth Member member, @PathVariable Long folderId
    ) {
        List<DocumentFindResult> documentResults;
        if (folderId.equals(ROOT_FOLDER_ID)) {
            documentResults = documentQueryService.findRootDocuments(member);
        } else {
            documentResults = documentQueryService.findDocuments(folderId);
        }

        List<DocumentFindResponse> responses = documentResults.stream().map(DocumentFindResponse::from).toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(
            @Auth Member member, @PathVariable Long folderId, @PathVariable Long id
    ) {
        documentService.deleteDocument(member, folderId, id);
        return ResponseEntity.noContent().build();
    }
}
