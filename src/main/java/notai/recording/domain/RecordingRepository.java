package notai.recording.domain;

import notai.common.exception.type.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import static notai.common.exception.ErrorMessages.RECORDING_NOT_FOUND;

public interface RecordingRepository extends JpaRepository<Recording, Long> {
    default Recording getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(RECORDING_NOT_FOUND));
    }
}
