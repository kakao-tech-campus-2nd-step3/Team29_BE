package notai.summary.domain;

import static notai.common.exception.ErrorMessages.SUMMARY_NOT_FOUND;

import java.util.Optional;
import notai.common.exception.type.NotFoundException;
import notai.document.domain.Document;
import notai.summary.query.SummaryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummaryRepository extends JpaRepository<Summary, Long>, SummaryQueryRepository {
    default Summary getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(SUMMARY_NOT_FOUND));
    }

    Optional<Summary> findByDocumentAndPageNumber(Document document, int pageNumber);
}
