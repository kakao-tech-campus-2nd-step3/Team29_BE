package notai.problem.domain;

import notai.common.exception.type.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    default Problem getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("해당 문제 정보를 찾을 수 없습니다."));
    }
}
