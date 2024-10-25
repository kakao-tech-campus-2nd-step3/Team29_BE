package notai.problem.domain;

import static notai.common.exception.ErrorMessages.PROBLEM_NOT_FOUND;

import java.util.Optional;
import notai.common.exception.type.NotFoundException;
import notai.document.domain.Document;
import notai.problem.query.ProblemQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long>, ProblemQueryRepository {
    default Problem getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(PROBLEM_NOT_FOUND));
    }

    Optional<Problem> findByDocumentAndPageNumber(Document document, Integer pageNumber);
}
