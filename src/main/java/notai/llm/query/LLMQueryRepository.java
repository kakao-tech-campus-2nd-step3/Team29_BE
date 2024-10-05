package notai.llm.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import notai.llm.domain.QLLM;
import notai.llm.domain.TaskStatus;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LLMQueryRepository {

    private final JPAQueryFactory queryFactory;

    public TaskStatus getTaskStatusBySummaryId(Long summaryId) {
        QLLM lLM = QLLM.lLM;

        return queryFactory
                .select(lLM.status)
                .from(lLM)
                .where(lLM.summary.id.eq(summaryId))
                .fetchOne();
    }
}
