package notai.problem.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import notai.problem.domain.QProblem;
import notai.problem.query.result.ProblemPageContentResult;

@RequiredArgsConstructor
public class ProblemQueryRepositoryImpl implements ProblemQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProblemPageContentResult> getPageNumbersAndContentByDocumentId(Long documentId) {
        QProblem problem = QProblem.problem;

        return queryFactory
                .select(Projections.constructor(
                        ProblemPageContentResult.class,
                        problem.pageNumber,
                        problem.content
                ))
                .from(problem)
                .where(problem.document.id.eq(documentId).and(problem.content.isNotNull()))
                .fetch();
    }

    @Override
    public String getProblemContentByDocumentIdAndPageNumber(Long documentId, Integer pageNumber) {
        QProblem problem = QProblem.problem;

        return queryFactory
                .select(problem.content)
                .from(problem)
                .where(problem.document.id.eq(documentId).and(problem.pageNumber.eq(pageNumber)))
                .fetchOne();
    }
}
