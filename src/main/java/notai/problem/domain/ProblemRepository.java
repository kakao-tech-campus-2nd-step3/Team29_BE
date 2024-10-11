package notai.problem.domain;

import notai.common.exception.type.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import static notai.common.exception.ErrorMessages.PROBLEM_NOT_FOUND;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    default Problem getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(PROBLEM_NOT_FOUND));
    }
}
