package me.hyunggeun.springbootdeveloper.article.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.article.entity.Article;
import me.hyunggeun.springbootdeveloper.article.entity.QArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.hyunggeun.springbootdeveloper.article.entity.QArticle.*;


@Repository
@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Article> findByKeyword(String keyword, Pageable pageable) {

        BooleanExpression predicate = null;

        if (keyword != null && !keyword.isEmpty()) {
            // keyword가 있을 때만 title과 content 조건을 or로 결합
            predicate = titleContains(keyword).or(contentContains(keyword));
        } else {
            // keyword가 없으면 전체 조회
            predicate = article.isNotNull();
        }

        QueryResults<Article> results = queryFactory
                .selectFrom(article)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(article.id.desc())
                .fetchResults();

        long total = results.getTotal();
        List<Article> articles = results.getResults();

        return new PageImpl<>(articles, pageable, total);


    }
    private BooleanExpression titleContains(String title) {
        return title != null ? article.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression contentContains(String content) {
        return content != null ? article.content.containsIgnoreCase(content) : null;
    }
}
