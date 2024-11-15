package notai.ocr.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import notai.ocr.domain.OCR;
import static notai.ocr.domain.QOCR.oCR;

import java.util.List;

@RequiredArgsConstructor
public class OCRQueryRepositoryImpl implements OCRQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<OCR> findAllByDocumentIdAndPageNumber(Long documentId, Integer pageNumber) {
        return queryFactory.selectFrom(oCR)
                           .where(oCR.document.id.eq(documentId).and(oCR.pageNumber.eq(pageNumber)))
                           .fetch();
    }
}
