package me.hyunggeun.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.domain.Article;
import me.hyunggeun.springbootdeveloper.dto.AddArticleRequest;
import me.hyunggeun.springbootdeveloper.dto.ArticleResponse;
import me.hyunggeun.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;


    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        List<ArticleResponse> articles = blogService.findAll().stream().map(a -> new ArticleResponse(a.getId(),a.getTitle(), a.getContent(),a.getCreatedAt())).toList();


        return ResponseEntity.status(HttpStatus.OK).body(articles);

    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ArticleResponse(article.getId(), article.getTitle(), article.getContent(),article.getCreatedAt()));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updatedArticle(@PathVariable long id,
                                                  @RequestBody AddArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(updatedArticle);
    }

}
