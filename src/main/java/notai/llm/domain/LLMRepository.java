package notai.llm.domain;

import notai.common.exception.type.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import static notai.common.exception.ErrorMessages.LLM_TASK_LOG_NOT_FOUND;

public interface LLMRepository extends JpaRepository<LLM, UUID> {
    default LLM getById(UUID id) {
        return findById(id).orElseThrow(() -> new NotFoundException(LLM_TASK_LOG_NOT_FOUND));
    }
}
