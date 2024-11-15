package notai.stt.query;

import notai.stt.domain.Stt;

import java.util.List;

public interface SttQueryRepository {
    List<Stt> findAllByDocumentIdAndPageNumber(Long documentId, Integer pageNumber);

    List<Stt> findAllByDocumentId(Long documentId);
}
