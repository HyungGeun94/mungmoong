package me.hyunggeun.springbootdeveloper.comment.repository;

import me.hyunggeun.springbootdeveloper.article.entity.Article;
import me.hyunggeun.springbootdeveloper.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByArticleAndParentIsNullOrderByIdAsc(Article article);

}