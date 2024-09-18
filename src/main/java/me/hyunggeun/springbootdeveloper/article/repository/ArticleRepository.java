package me.hyunggeun.springbootdeveloper.article.repository;

import me.hyunggeun.springbootdeveloper.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<Article, Long> , ArticleRepositoryCustom {

}
