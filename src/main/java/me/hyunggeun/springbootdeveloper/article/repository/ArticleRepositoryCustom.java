package me.hyunggeun.springbootdeveloper.article.repository;

import me.hyunggeun.springbootdeveloper.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleRepositoryCustom {

    Page<Article> findByKeyword(String keyword, Pageable pageable);

}
