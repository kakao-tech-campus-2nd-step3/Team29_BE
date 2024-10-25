package notai.ocr.application;

import lombok.RequiredArgsConstructor;
import static notai.common.exception.ErrorMessages.OCR_RESULT_NOT_FOUND;
import notai.common.exception.type.NotFoundException;
import notai.ocr.application.result.OCRFindResult;
import notai.ocr.domain.OCR;
import notai.ocr.domain.OCRRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OCRQueryService {

    private final OCRRepository ocrRepository;

    public OCRFindResult findOCR(Long documentId, Integer pageNumber) {
        OCR ocr = ocrRepository.findOCRByDocumentIdAndPageNumber(documentId,
                pageNumber
        ).orElseThrow(() -> new NotFoundException(OCR_RESULT_NOT_FOUND));

        return OCRFindResult.of(documentId, pageNumber, ocr.getContent());
    }
}
