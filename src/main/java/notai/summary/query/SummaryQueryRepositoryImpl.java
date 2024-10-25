package notai.summary.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import notai.summary.domain.QSummary;
import notai.summary.query.result.SummaryPageContentResult;

@RequiredArgsConstructor
public class SummaryQueryRepositoryImpl implements SummaryQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> getSummaryIdsByDocumentId(Long documentId) {
        QSummary summary = QSummary.summary;

        return queryFactory
                .select(summary.id)
                .from(summary)
                .where(summary.document.id.eq(documentId))
                .fetch();
    }

    @Override
    public List<SummaryPageContentResult> getPageNumbersAndContentByDocumentId(Long documentId) {
        QSummary summary = QSummary.summary;

        return queryFactory
                .select(Projections.constructor(
                        SummaryPageContentResult.class,
                        summary.pageNumber,
                        summary.content
                ))
                .from(summary)
                .where(summary.document.id.eq(documentId).and(summary.content.isNotNull()))
                .fetch();
    }

    @Override
    public Long getSummaryIdByDocumentIdAndPageNumber(Long documentId, Integer pageNumber) {
        QSummary summary = QSummary.summary;

        return queryFactory
                .select(summary.id)
                .from(summary)
                .where(summary.document.id.eq(documentId).and(summary.pageNumber.eq(pageNumber)))
                .fetchOne();
    }

    @Override
    public String getSummaryContentByDocumentIdAndPageNumber(Long documentId, Integer pageNumber) {
        QSummary summary = QSummary.summary;

        return queryFactory
                .select(summary.content)
                .from(summary)
                .where(summary.document.id.eq(documentId).and(summary.pageNumber.eq(pageNumber)))
                .fetchOne();
    }
}
