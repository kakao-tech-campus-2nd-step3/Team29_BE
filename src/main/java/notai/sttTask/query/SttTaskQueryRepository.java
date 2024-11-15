package notai.sttTask.query;

import notai.llm.domain.TaskStatus;

public interface SttTaskQueryRepository {
    TaskStatus getTaskStatusByDocumentIdAndPageNumber(Long documentId, Integer pageNumber);
}
