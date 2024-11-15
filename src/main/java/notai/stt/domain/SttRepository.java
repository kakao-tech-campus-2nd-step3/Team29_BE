package notai.stt.domain;

import notai.stt.query.SttQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SttRepository extends JpaRepository<Stt, Long>, SttQueryRepository {
}
