package notai.summary.domain;

import notai.common.exception.type.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import static notai.common.exception.ErrorMessages.SUMMARY_NOT_FOUND;

public interface SummaryRepository extends JpaRepository<Summary, Long> {
    default Summary getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(SUMMARY_NOT_FOUND));
    }
}
