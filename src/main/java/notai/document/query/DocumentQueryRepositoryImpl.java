package notai.document.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DocumentQueryRepositoryImpl implements DocumentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
}
