package notai.stt.application;

import lombok.RequiredArgsConstructor;
import notai.document.domain.Document;
import notai.document.domain.DocumentRepository;
import notai.member.domain.Member;
import notai.member.domain.MemberRepository;
import notai.stt.domain.Stt;
import notai.stt.domain.SttRepository;
import notai.stt.presentation.response.SttPageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SttQueryService {

    private final SttRepository sttRepository;
    private final MemberRepository memberRepository;
    private final DocumentRepository documentRepository;

    public SttPageResponse getSttByPage(Long memberId, Long documentId, Integer pageNumber) {
        Member member = memberRepository.getById(memberId);
        Document foundDocument = documentRepository.getById(documentId);
        foundDocument.validateOwner(member);

        List<Stt> sttResults = sttRepository.findAllByDocumentIdAndPageNumber(documentId, pageNumber);
        return SttPageResponse.of(pageNumber, sttResults);
    }
} 
