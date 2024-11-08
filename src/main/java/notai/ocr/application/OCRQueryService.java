package notai.ocr.application;

import lombok.RequiredArgsConstructor;
import notai.common.exception.type.NotFoundException;
import notai.member.domain.Member;
import notai.ocr.application.result.OCRFindResult;
import notai.ocr.domain.OCR;
import notai.ocr.domain.OCRRepository;
import org.springframework.stereotype.Service;

import static notai.common.exception.ErrorMessages.OCR_RESULT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OCRQueryService {

    private final OCRRepository ocrRepository;

    public OCRFindResult findOCR(Member member, Long documentId, Integer pageNumber) {
        OCR ocr = ocrRepository.findOCRByDocumentIdAndPageNumber(documentId, pageNumber
        ).orElseThrow(() -> new NotFoundException(OCR_RESULT_NOT_FOUND));
        ocr.getDocument().validateOwner(member);

        return OCRFindResult.of(documentId, pageNumber, ocr.getContent());
    }
}
