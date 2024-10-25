package notai.llm.domain;

import static notai.common.exception.ErrorMessages.LLM_TASK_LOG_NOT_FOUND;
import notai.common.exception.type.NotFoundException;
import notai.problem.domain.Problem;
import notai.summary.domain.Summary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LlmTaskRepository extends JpaRepository<LlmTask, UUID> {
    default LlmTask getById(UUID id) {
        return findById(id).orElseThrow(() -> new NotFoundException(LLM_TASK_LOG_NOT_FOUND));
    }

    Optional<LlmTask> findBySummaryAndProblem(Summary summary, Problem problem);

    default LlmTask getBySummaryAndProblem(Summary summary, Problem problem) {
        return findBySummaryAndProblem(summary, problem)
                .orElseThrow(() -> new NotFoundException(LLM_TASK_LOG_NOT_FOUND));
    }
}
