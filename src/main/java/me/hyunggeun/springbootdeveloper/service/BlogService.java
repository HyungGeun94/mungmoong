package me.hyunggeun.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.domain.Article;
import me.hyunggeun.springbootdeveloper.dto.AddArticleRequest;
import me.hyunggeun.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {

        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("not found" +id)
        );
    }


    public void delete(Long id) {
        blogRepository.deleteById(id);
    }


    @Transactional
    public Article update(Long id, AddArticleRequest request) {
        Article article = findById(id);

        article.update(request.getTitle(),request.getContent());

        return article;


    }

}
