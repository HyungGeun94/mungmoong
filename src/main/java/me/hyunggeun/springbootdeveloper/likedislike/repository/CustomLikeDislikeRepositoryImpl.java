package me.hyunggeun.springbootdeveloper.likedislike.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.likedislike.entity.QLikeDislike;

import static me.hyunggeun.springbootdeveloper.likedislike.entity.QLikeDislike.*;


@RequiredArgsConstructor
public class CustomLikeDislikeRepositoryImpl implements CustomLikeDislikeRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public int likeCount(Long postId) {

        long count = queryFactory
                .selectFrom(likeDislike)
                .where(likeDislike.likeStatus.eq(true)
                        , likeDislike.article.id.eq(postId))
                .fetchCount();


        return (int)count;
    }

    @Override
    public int dislikeCount(Long postId) {

        long count = queryFactory
                .selectFrom(likeDislike)
                .where(likeDislike.likeStatus.eq(false)
                        , likeDislike.article.id.eq(postId))
                .fetchCount();


        return (int)count;
    }
}
