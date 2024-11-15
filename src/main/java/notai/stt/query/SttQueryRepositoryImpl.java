package notai.stt.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import static notai.stt.domain.QStt.stt;
import notai.stt.domain.Stt;
import static notai.sttTask.domain.QSttTask.sttTask;

import java.util.List;

@RequiredArgsConstructor
public class SttQueryRepositoryImpl implements SttQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Stt> findAllByDocumentIdAndPageNumber(Long documentId, Integer pageNumber) {
        return queryFactory
                .selectFrom(stt)
                .join(stt.sttTask, sttTask).fetchJoin()
                .join(sttTask.recording).fetchJoin()
                .join(sttTask.recording.document).fetchJoin()
                .where(sttTask.recording.document.id.eq(documentId)
                        .and(stt.pageNumber.eq(pageNumber)))
                .orderBy(stt.pageNumber.asc())
                .fetch();
    }

    @Override
    public List<Stt> findAllByDocumentId(Long documentId) {
        return queryFactory
                .selectFrom(stt)
                .join(stt.sttTask, sttTask).fetchJoin()
                .join(sttTask.recording).fetchJoin()
                .join(sttTask.recording.document).fetchJoin()
                .where(sttTask.recording.document.id.eq(documentId))
                .orderBy(stt.pageNumber.asc())
                .fetch();
    }
}
