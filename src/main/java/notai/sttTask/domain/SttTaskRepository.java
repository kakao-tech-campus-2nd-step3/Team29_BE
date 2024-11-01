package notai.sttTask.domain;

import static notai.common.exception.ErrorMessages.AI_SERVER_ERROR;
import notai.common.exception.type.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SttTaskRepository extends JpaRepository<SttTask, UUID> {

    default SttTask getById(UUID id) {
        return findById(id).orElseThrow(() -> new NotFoundException(AI_SERVER_ERROR));
    }
}
