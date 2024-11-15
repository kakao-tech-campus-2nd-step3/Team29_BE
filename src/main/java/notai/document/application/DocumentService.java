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
import notai.member.domain.Member;
import notai.member.domain.MemberRepository;
import notai.ocr.application.OCRService;
import notai.pdf.PdfService;
import notai.pdf.result.PdfSaveResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final PdfService pdfService;
    private final OCRService ocrService;
    private final DocumentRepository documentRepository;
    private final FolderRepository folderRepository;
    private final MemberRepository memberRepository;

    private static final Long ROOT_FOLDER_ID = -1L;

    public DocumentSaveResult saveDocument(
            Long memberId, Long folderId, MultipartFile pdfFile, DocumentSaveRequest documentSaveRequest
    ) {
        PdfSaveResult pdfSaveResult = pdfService.savePdf(pdfFile);
        Document document = saveAndReturnDocument(memberId, folderId, documentSaveRequest, pdfSaveResult);
        ocrService.saveOCR(document, pdfSaveResult.pdf());
        return DocumentSaveResult.of(document.getId(), document.getName(), document.getUrl());
    }

    public DocumentSaveResult saveRootDocument(
            Long memberId, MultipartFile pdfFile, DocumentSaveRequest documentSaveRequest
    ) {
        PdfSaveResult pdfSaveResult = pdfService.savePdf(pdfFile);
        Document document = saveAndReturnRootDocument(memberId, documentSaveRequest, pdfSaveResult);
        ocrService.saveOCR(document, pdfSaveResult.pdf());
        return DocumentSaveResult.of(document.getId(), document.getName(), document.getUrl());
    }

    public DocumentUpdateResult updateDocument(
            Long memberId, Long folderId, Long documentId, DocumentUpdateRequest documentUpdateRequest
    ) {
        Document document = documentRepository.getById(documentId);
        Member member = memberRepository.getById(memberId);
        document.validateOwner(member);

        if (!folderId.equals(ROOT_FOLDER_ID)) {
            document.validateDocument(folderId);
        }
        document.updateName(documentUpdateRequest.name());
        Document savedDocument = documentRepository.save(document);
        return DocumentUpdateResult.of(savedDocument.getId(), savedDocument.getName(), savedDocument.getUrl());
    }

    public void deleteDocument(
            Long memberId, Long folderId, Long documentId
    ) {
        Document document = documentRepository.getById(documentId);
        Member member = memberRepository.getById(memberId);
        document.validateOwner(member);

        if (!folderId.equals(ROOT_FOLDER_ID)) {
            document.validateDocument(folderId);
        }
        ocrService.deleteAllByDocument(document);
        documentRepository.delete(document);
    }

    public void deleteAllByFolder(
            Long memberId, Folder folder
    ) {
        List<Document> documents = documentRepository.findAllByFolderId(folder.getId());
        for (Document document : documents) {
            deleteDocument(memberId, folder.getId(), document.getId());
        }
    }

    private Document saveAndReturnDocument(
            Long memberId, Long folderId, DocumentSaveRequest documentSaveRequest, PdfSaveResult pdfSaveResult
    ) {
        Folder folder = folderRepository.getById(folderId);
        Member member = memberRepository.getById(memberId);
        Document document = new Document(folder,
                member,
                documentSaveRequest.name(),
                pdfSaveResult.pdfUrl(),
                pdfSaveResult.totalPages()
        );
        return documentRepository.save(document);
    }

    private Document saveAndReturnRootDocument(
            Long memberId, DocumentSaveRequest documentSaveRequest, PdfSaveResult pdfSaveResult
    ) {
        Member member = memberRepository.getById(memberId);
        Document document = new Document(member,
                documentSaveRequest.name(),
                pdfSaveResult.pdfUrl(),
                pdfSaveResult.totalPages()
        );
        return documentRepository.save(document);
    }
}
