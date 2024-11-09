package notai.document.application;

import lombok.RequiredArgsConstructor;
import notai.document.application.result.DocumentFindResult;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import notai.member.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentQueryService {

    private final DocumentRepository documentRepository;

    public List<DocumentFindResult> findDocuments(Long folderId) {
        List<Document> documents = documentRepository.findAllByFolderId(folderId);
        return documents.stream().map(this::getDocumentFindResult).toList();
    }

    public List<DocumentFindResult> findRootDocuments(Member member) {
        List<Document> documents = documentRepository.findAllByMemberIdAndFolderIdIsNull(member.getId());
        return documents.stream().map(this::getDocumentFindResult).toList();
    }

    private DocumentFindResult getDocumentFindResult(Document document) {
        return DocumentFindResult.of(document.getId(), document.getName(), document.getUrl());
    }
}
