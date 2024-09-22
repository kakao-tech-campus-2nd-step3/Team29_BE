package notai.aiTask.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AITaskRepository extends JpaRepository<AITask, Long> {

}
