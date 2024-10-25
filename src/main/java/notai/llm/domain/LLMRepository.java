package notai.llm.domain;

import static notai.common.exception.ErrorMessages.LLM_TASK_LOG_NOT_FOUND;

import java.util.Optional;
import java.util.UUID;
import notai.common.exception.type.NotFoundException;
import notai.problem.domain.Problem;
import notai.summary.domain.Summary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LLMRepository extends JpaRepository<LLM, UUID> {
    default LLM getById(UUID id) {
        return findById(id).orElseThrow(() -> new NotFoundException(LLM_TASK_LOG_NOT_FOUND));
    }

    Optional<LLM> findBySummaryAndProblem(Summary summary, Problem problem);

    default LLM getBySummaryAndProblem(Summary summary, Problem problem) {
        return findBySummaryAndProblem(summary, problem)
                .orElseThrow(() -> new NotFoundException(LLM_TASK_LOG_NOT_FOUND));
    }
}
