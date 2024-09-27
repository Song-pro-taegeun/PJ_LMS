package com.pro.lms.repository;

import com.pro.lms.dto.BoardMemberDto;
import com.pro.lms.dto.QBoardMemberDto;
import com.pro.lms.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.pro.lms.entity.QPost.post;
import static com.pro.lms.entity.QUser.user;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private  final JPAQueryFactory jpaQueryFactory;

    // queryDsl TEST
    @Override
    public List<Post> queryDslTest(long userId) {
        return jpaQueryFactory
                .selectFrom(post)
                .where(post.userId.eq(userId))
                .fetch(); // 쿼리문을 처리 한 후, 반환되는 값들을 그대로 리스트로 가져옴. 이떄, 반환되는 데이터가 없으면 빈 리스트(Empty List)가 반환
//                .fetchOne(); // 단 하나의 데이터를 조회. 쿼리문에 의해 반환되는 데이터가 0개 이면 NULL 반환, 1개이면 정상, 그리고 2개 이상일 경우에는 NonUniqueResultException가 발생.
//                .fetchFirst(); // limit(1).fetchOne()과 동일, 항상 한개의 데이터만 조회.
//                .fetchResults(); // 페이징 정보 포함 & count 쿼리도 같이 실행. But, deprecated.
//                .fetchCount(); // 쿼리를 count 쿼리로 변경해서, count 값일 반환. But, deprecated.
    }


    // dto에서 @QueryProjection를 작성하여 생성자를 만들어줄 경우 셀렉트 시 바로 DTO에 매핑이 가능하다.
    @Override
    public List<BoardMemberDto> queryDslJoinTest(long userId) {
        return jpaQueryFactory
                .select(new QBoardMemberDto
                        (
                            post.id, post.title, post.content,  post.userId,
                            user.username, user.email
                        )
                    )
                .from(post)
                .leftJoin(user).on(post.userId.eq(user.id))
                .where(post.userId.eq(userId))
                .fetch();

//        return
//        jpaQueryFactory
//        .select(
//            post.id, post.userId, post.title, post.content
//            , user.email, user.username
//        )
//        .from(post)
//        .leftJoin(user).on(post.userId.eq(user.id))
//        .where(post.userId.eq(userId))
//        .fetch();
    }
}
