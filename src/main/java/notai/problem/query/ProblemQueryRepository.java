package notai.problem.query;

import java.util.List;
import notai.problem.query.result.ProblemPageContentResult;

public interface ProblemQueryRepository {

    List<ProblemPageContentResult> getPageNumbersAndContentByDocumentId(Long documentId);

    String getProblemContentByDocumentIdAndPageNumber(Long documentId, Integer pageNumber);
}
