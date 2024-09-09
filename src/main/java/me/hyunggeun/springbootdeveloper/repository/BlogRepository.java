package me.hyunggeun.springbootdeveloper.repository;

import me.hyunggeun.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogRepository extends JpaRepository<Article, Long> {

}
