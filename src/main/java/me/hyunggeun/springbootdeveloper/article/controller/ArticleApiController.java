package me.hyunggeun.springbootdeveloper.article.controller;

import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.article.dto.AddArticleRequest;
import me.hyunggeun.springbootdeveloper.article.entity.Article;
import me.hyunggeun.springbootdeveloper.article.dto.ArticleResponse;
import me.hyunggeun.springbootdeveloper.article.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleApiController {

    private final ArticleService articleService;


    @PostMapping("/api/articles")
    public ResponseEntity<?> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = articleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle.getId());
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {
        Article article = articleService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ArticleResponse(article.getId(), article.getTitle(), article.getContent(),article.getCreatedAt(),article.getUser().getEmail(),0,0));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updatedArticle(
            @PathVariable long id,
            @RequestBody AddArticleRequest request
    ) {

        Article updatedArticle = articleService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(new ArticleResponse(updatedArticle.getId(), updatedArticle.getTitle(), updatedArticle.getContent(),updatedArticle.getCreatedAt(),updatedArticle.getUser().getEmail(),0,0));
    }

}
