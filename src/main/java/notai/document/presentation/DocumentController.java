package notai.document.presentation;

import lombok.RequiredArgsConstructor;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/folders/{folderId}/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentQueryService documentQueryService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<DocumentSaveResponse> saveDocument(
            @PathVariable Long folderId,
            @RequestPart MultipartFile pdfFile,
            @RequestPart DocumentSaveRequest documentSaveRequest
    ) {
        DocumentSaveResult documentSaveResult;
        if (folderId.equals(-1L)) {
            documentSaveResult = documentService.saveRootDocument(pdfFile, documentSaveRequest);
        } else {
            documentSaveResult = documentService.saveDocument(folderId, pdfFile, documentSaveRequest);
        }
        DocumentSaveResponse response = DocumentSaveResponse.from(documentSaveResult);
        String url = String.format("/api/folders/%s/documents/%s", folderId, response.id());
        return ResponseEntity.created(URI.create(url)).body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DocumentUpdateResponse> updateDocument(
            @PathVariable Long folderId, @PathVariable Long id, @RequestBody DocumentUpdateRequest documentUpdateRequest
    ) {
        DocumentUpdateResult documentUpdateResult = documentService.updateDocument(folderId, id, documentUpdateRequest);
        DocumentUpdateResponse response = DocumentUpdateResponse.from(documentUpdateResult);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DocumentFindResponse>> getDocuments(
            @PathVariable Long folderId
    ) {
        List<DocumentFindResult> documentResults = documentQueryService.findDocuments(folderId);
        List<DocumentFindResponse> responses = documentResults.stream().map(DocumentFindResponse::from).toList();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> getDocuments(
            @PathVariable Long folderId, @PathVariable Long id
    ) {
        documentService.deleteDocument(folderId, id);
        return ResponseEntity.noContent().build();
    }
}
