package me.hyunggeun.springbootdeveloper.controller;


import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.domain.Article;
import me.hyunggeun.springbootdeveloper.dto.ArticleResponse;
import me.hyunggeun.springbootdeveloper.dto.CommentResponse;
import me.hyunggeun.springbootdeveloper.service.BlogService;
import me.hyunggeun.springbootdeveloper.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    private final CommentService commentService;


    @GetMapping("/articles")
    public String articles(Model model) {


        List<ArticleResponse> articles = blogService.findAll().stream().map(a -> new ArticleResponse(a.getId(), a.getTitle(), a.getContent(),a.getCreatedAt())).toList();

        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article",new ArticleResponse(article.getId(), article.getTitle(), article.getContent(), article.getCreatedAt()));


        List<CommentResponse> list = commentService.findByArticleId(id).stream().map(c -> new CommentResponse(c.getUser().getEmail(), c.getContent(), c.getCreatedAt(), c.getUpdatedAt())).toList();


        model.addAttribute("comments", list);

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleResponse());
        }else{
            Article article = blogService.findById(id);
            model.addAttribute("article",new ArticleResponse(article.getId(), article.getTitle(), article.getContent(), article.getCreatedAt()));
        }

        return "newArticle";
    }
}
