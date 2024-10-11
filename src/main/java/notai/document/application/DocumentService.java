package notai.document.application;

import lombok.RequiredArgsConstructor;
import notai.document.application.result.DocumentSaveResult;
import notai.document.application.result.DocumentUpdateResult;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import notai.document.presentation.request.DocumentSaveRequest;
import notai.document.presentation.request.DocumentUpdateRequest;
import notai.folder.domain.Folder;
import notai.folder.domain.FolderRepository;
import notai.ocr.application.OCRService;
import notai.pdf.PdfService;
import notai.pdf.result.PdfSaveResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final PdfService pdfService;
    private final OCRService ocrService;
    private final DocumentRepository documentRepository;
    private final FolderRepository folderRepository;

    public DocumentSaveResult saveDocument(
            Long folderId, MultipartFile pdfFile, DocumentSaveRequest documentSaveRequest
    ) {
        PdfSaveResult pdfSaveResult = pdfService.savePdf(pdfFile);
        Document document = saveAndReturnDocument(folderId, documentSaveRequest, pdfSaveResult.pdfUrl());
        ocrService.saveOCR(document, pdfSaveResult.pdf());
        return DocumentSaveResult.of(document.getId(), document.getName(), document.getUrl());
    }

    public DocumentSaveResult saveRootDocument(
            MultipartFile pdfFile, DocumentSaveRequest documentSaveRequest
    ) {
        PdfSaveResult pdfSaveResult = pdfService.savePdf(pdfFile);
        Document document = saveAndReturnRootDocument(documentSaveRequest, pdfSaveResult.pdfUrl());
        ocrService.saveOCR(document, pdfSaveResult.pdf());
        return DocumentSaveResult.of(document.getId(), document.getName(), document.getUrl());
    }

    public DocumentUpdateResult updateDocument(
            Long folderId, Long documentId, DocumentUpdateRequest documentUpdateRequest
    ) {
        Document document = documentRepository.getById(documentId);
        document.validateDocument(folderId);
        document.updateName(documentUpdateRequest.name());
        Document savedDocument = documentRepository.save(document);
        return DocumentUpdateResult.of(savedDocument.getId(), savedDocument.getName(), savedDocument.getUrl());
    }

    public void deleteDocument(
            Long folderId, Long documentId
    ) {
        Document document = documentRepository.getById(documentId);
        document.validateDocument(folderId);
        documentRepository.delete(document);
    }

    public void deleteAllByFolder(
            Folder folder
    ) {
        documentRepository.deleteAllByFolder(folder);
    }

    private Document saveAndReturnDocument(Long folderId, DocumentSaveRequest documentSaveRequest, String pdfUrl) {
        Folder folder = folderRepository.getById(folderId);
        Document document = new Document(folder, documentSaveRequest.name(), pdfUrl);
        return documentRepository.save(document);
    }

    private Document saveAndReturnRootDocument(DocumentSaveRequest documentSaveRequest, String pdfUrl) {
        Document document = new Document(documentSaveRequest.name(), pdfUrl);
        return documentRepository.save(document);
    }
}
