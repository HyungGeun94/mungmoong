package me.hyunggeun.springbootdeveloper.article.controller;


import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.article.dto.PageDTO;
import me.hyunggeun.springbootdeveloper.article.entity.Article;
import me.hyunggeun.springbootdeveloper.article.dto.ArticleResponse;
import me.hyunggeun.springbootdeveloper.article.repository.ArticleRepository;
import me.hyunggeun.springbootdeveloper.article.service.ArticleService;
import me.hyunggeun.springbootdeveloper.comment.dto.CommentResponse;
import me.hyunggeun.springbootdeveloper.comment.dto.ReCommentResponse;
import me.hyunggeun.springbootdeveloper.comment.service.CommentService;
import me.hyunggeun.springbootdeveloper.likedislike.service.LikeDislikeService;
import me.hyunggeun.springbootdeveloper.security.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ArticleViewController {

    private final ArticleService articleService;

    private final CommentService commentService;

    private final ArticleRepository articleRepository;

    private final LikeDislikeService likeDislikeService;


    // 모든 글 목록을 표시하는 핸들러
    @GetMapping({"/articles","/"})
    public String articles(@RequestParam(required = false) String keyword,
                           Model model,
                           @PageableDefault(page=0,size=5)Pageable pageable) {
        // keyword가 전달되면 그에 맞게 검색, 아니면 전체 조회
//        Page<ArticleResponse> articles = blogService.findByKeyword(keyword, pageable).stream()
//                .map(a -> new ArticleResponse(a.getId(), a.getTitle(), a.getContent(), a.getCreatedAt()))
//                .toList();

        PageDTO<ArticleResponse> pageDTO = articleService.findByKeyword(keyword, pageable);

        Page<ArticleResponse> articles = new PageImpl<>(pageDTO.getContent(), pageable, pageDTO.getTotalElements());


        model.addAttribute("articles", articles);
        model.addAttribute("currentPage", articles.getNumber());
        model.addAttribute("totalPages", articles.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id);

        model.addAttribute("article",new ArticleResponse(article.getId(), article.getTitle(), article.getContent(), article.getCreatedAt(),article.getUser().getEmail(), likeDislikeService.likeCount(id), likeDislikeService.dislikeCount(id)));


        List<CommentResponse> list = commentService.findByArticleId(id).stream().map(c -> new CommentResponse(c.getId(), c.getUser().getEmail(), c.getContent(), c.getCreatedAt(), c.getUpdatedAt(), c.getReplies().stream()
                .map((r) -> new ReCommentResponse(r.getId(), r.getUser().getEmail(), r.getContent(), r.getCreatedAt(), r.getUpdatedAt())).toList())).toList();


        model.addAttribute("comments", list);

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleResponse());
        }else{

            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Article article = articleService.findById(id);


            if(!customUserDetails.getUsername().equals(article.getUser().getEmail())) {

                throw new IllegalArgumentException("User not authorized");

            }

            model.addAttribute("article",new ArticleResponse(article.getId(), article.getTitle(), article.getContent(), article.getCreatedAt(),article.getUser().getEmail(),0,0));
        }

        return "newArticle";
    }
}
