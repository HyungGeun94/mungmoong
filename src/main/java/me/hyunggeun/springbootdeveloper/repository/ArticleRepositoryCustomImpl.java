package me.hyunggeun.springbootdeveloper.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.domain.Article;
import me.hyunggeun.springbootdeveloper.domain.QArticle;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.hyunggeun.springbootdeveloper.domain.QArticle.article;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Article> findByTitleAndContent(String title, String content) {
        return queryFactory
                .selectFrom(article)
                .where(
                        titleContains(title),
                        contentContains(content)
                )
                .fetch();
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? article.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression contentContains(String content) {
        return content != null ? article.content.containsIgnoreCase(content) : null;
    }
}
