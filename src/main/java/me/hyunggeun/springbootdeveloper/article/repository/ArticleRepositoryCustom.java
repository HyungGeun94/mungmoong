package me.hyunggeun.springbootdeveloper.article.repository;

import me.hyunggeun.springbootdeveloper.article.entity.Article;

import java.util.List;

public interface ArticleRepositoryCustom {

    List<Article> findByTitleAndContent(String title, String content);

}
