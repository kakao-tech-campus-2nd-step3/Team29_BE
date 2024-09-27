package notai.llm.domain;

import notai.common.exception.type.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LLMRepository extends JpaRepository<LLM, UUID> {
    default LLM getById(UUID id) {
        return findById(id).orElseThrow(() -> new NotFoundException("해당 작업 기록을 찾을 수 없습니다."));
    }
}
