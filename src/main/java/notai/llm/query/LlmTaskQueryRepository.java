package notai.llm.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import notai.llm.domain.QLlmTask;
import notai.llm.domain.TaskStatus;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LlmTaskQueryRepository {

    private final JPAQueryFactory queryFactory;

    public TaskStatus getTaskStatusBySummaryId(Long summaryId) {
        QLlmTask llmTask = QLlmTask.llmTask;

        return queryFactory
                .select(llmTask.status)
                .from(llmTask)
                .where(llmTask.summary.id.eq(summaryId))
                .fetchOne();
    }
}
