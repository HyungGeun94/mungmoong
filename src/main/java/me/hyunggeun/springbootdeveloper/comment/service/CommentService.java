package me.hyunggeun.springbootdeveloper.comment.service;

import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.article.entity.Article;
import me.hyunggeun.springbootdeveloper.comment.entity.Comment;
import me.hyunggeun.springbootdeveloper.comment.repository.CommentRepository;
import me.hyunggeun.springbootdeveloper.comment.dto.CommentRequest;
import me.hyunggeun.springbootdeveloper.security.CustomUserDetails;
import me.hyunggeun.springbootdeveloper.user.entity.User;
import me.hyunggeun.springbootdeveloper.article.repository.ArticleRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository blogRepository;


    @Transactional
    public Long save(CommentRequest commentRequest) {


        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDetails.getUser();

        Article article = blogRepository.findById(commentRequest.getArticleId()).orElseThrow(() -> new IllegalArgumentException("없는 글입니다"));

        Comment comment = commentRepository.findById(commentRequest.getParentId()).orElseGet(()->null);





        if(commentRequest.getParentId()==0) {
             comment = Comment.builder()
                    .content(commentRequest.getContent())
                    .user(user)
                    .article(article)
                    .parent(null)
                    .build();
        }else{
            comment = Comment.builder()
                    .content(commentRequest.getContent())
                    .user(user)
                    .article(article)
                    .parent(comment)
                    .build();

        }


        return commentRepository.save(comment).getId();

    }

    public List<Comment> findByArticleId(Long articleId) {


        Article article = blogRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("없습니다."));
        return commentRepository.findByArticleAndParentIsNullOrderByIdAsc(article);

    }



}
