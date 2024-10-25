package notai.summary.query;

import java.util.List;
import notai.summary.query.result.SummaryPageContentResult;

public interface SummaryQueryRepository {

    List<Long> getSummaryIdsByDocumentId(Long documentId);

    List<SummaryPageContentResult> getPageNumbersAndContentByDocumentId(Long documentId);

    Long getSummaryIdByDocumentIdAndPageNumber(Long documentId, Integer pageNumber);

    String getSummaryContentByDocumentIdAndPageNumber(Long documentId, Integer pageNumber);
}
