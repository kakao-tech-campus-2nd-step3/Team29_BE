package notai.ocr.application;

import lombok.RequiredArgsConstructor;
import static notai.common.exception.ErrorMessages.OCR_RESULT_NOT_FOUND;
import notai.common.exception.type.NotFoundException;
import notai.member.domain.Member;
import notai.member.domain.MemberRepository;
import notai.ocr.application.result.OCRFindResult;
import notai.ocr.domain.OCR;
import notai.ocr.domain.OCRRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OCRQueryService {

    private final OCRRepository ocrRepository;
    private final MemberRepository memberRepository;

    public OCRFindResult findOCR(Long memberId, Long documentId, Integer pageNumber) {
        Member member = memberRepository.getById(memberId);

        OCR ocr = ocrRepository.findOCRByDocumentIdAndPageNumber(
                documentId,
                pageNumber
        ).orElseThrow(() -> new NotFoundException(OCR_RESULT_NOT_FOUND));
        ocr.getDocument().validateOwner(member);

        return OCRFindResult.of(documentId, pageNumber, ocr.getContent());
    }
}
