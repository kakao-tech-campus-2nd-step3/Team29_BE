package notai.folder.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FolderQueryRepositoryImpl implements FolderQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
}
