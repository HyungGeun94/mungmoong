package me.hyunggeun.springbootdeveloper.article.controller;

import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.article.dto.AddArticleRequest;
import me.hyunggeun.springbootdeveloper.article.entity.Article;
import me.hyunggeun.springbootdeveloper.article.dto.ArticleResponse;
import me.hyunggeun.springbootdeveloper.article.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleApiController {

    private final ArticleService blogService;


    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        List<ArticleResponse> articles = blogService.findAll().stream().map(a -> new ArticleResponse(a.getId(),a.getTitle(), a.getContent(),a.getCreatedAt(),a.getUser().getEmail(),0,0)).toList();


        return ResponseEntity.status(HttpStatus.OK).body(articles);

    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ArticleResponse(article.getId(), article.getTitle(), article.getContent(),article.getCreatedAt(),article.getUser().getEmail(),0,0));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updatedArticle(@PathVariable long id,
                                                  @RequestBody AddArticleRequest request
    ) {


        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(updatedArticle);
    }

}
