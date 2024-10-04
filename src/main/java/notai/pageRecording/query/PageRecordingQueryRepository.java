package notai.pageRecording.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PageRecordingQueryRepository {

    private final JPAQueryFactory queryFactory;
}
