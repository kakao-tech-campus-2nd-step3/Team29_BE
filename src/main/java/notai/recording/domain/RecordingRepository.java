package notai.recording.domain;

import notai.common.exception.type.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordingRepository extends JpaRepository<Recording, Long> {
    default Recording getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("해당 녹음 정보를 찾을 수 없습니다."));
    }
}
