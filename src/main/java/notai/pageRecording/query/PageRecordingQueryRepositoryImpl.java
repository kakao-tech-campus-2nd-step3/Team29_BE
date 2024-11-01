package notai.pageRecording.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import notai.pageRecording.domain.PageRecording;
import static notai.pageRecording.domain.QPageRecording.pageRecording;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PageRecordingQueryRepositoryImpl implements PageRecordingQueryRepository{

    private final JPAQueryFactory queryFactory;

    public List<PageRecording> findAllByRecordingIdOrderByStartTime(Long recordingId) {
        return queryFactory.selectFrom(pageRecording)
                           .where(pageRecording.recording.id.eq(recordingId))
                           .orderBy(pageRecording.startTime.asc())
                           .fetch();
    }
}
