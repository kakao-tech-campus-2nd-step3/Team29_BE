package notai.problem.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import notai.problem.domain.QProblem;
import notai.problem.query.result.ProblemPageContentResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProblemQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Long> getProblemIdsByDocumentId(Long documentId) {
        QProblem problem = QProblem.problem;

        return queryFactory
                .select(problem.id)
                .from(problem)
                .where(problem.document.id.eq(documentId))
                .fetch();
    }

    public List<ProblemPageContentResult> getPageNumbersAndContentByDocumentId(Long documentId) {
        QProblem problem = QProblem.problem;

        return queryFactory
                .select(Projections.constructor(
                        ProblemPageContentResult.class,
                        problem.pageNumber,
                        problem.content
                ))
                .from(problem)
                .where(problem.document.id.eq(documentId)
                        .and(problem.content.isNotNull()))
                .fetch();
    }
}
