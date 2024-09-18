package me.hyunggeun.springbootdeveloper.repository;

import me.hyunggeun.springbootdeveloper.domain.Article;

import java.util.List;

public interface ArticleRepositoryCustom {

    List<Article> findByTitleAndContent(String title, String content);

}
