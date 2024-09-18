package me.hyunggeun.springbootdeveloper.article.service;

import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.article.dto.AddArticleRequest;
import me.hyunggeun.springbootdeveloper.article.entity.Article;
import me.hyunggeun.springbootdeveloper.article.repository.ArticleRepository;
import me.hyunggeun.springbootdeveloper.security.CustomUserDetails;
import me.hyunggeun.springbootdeveloper.user.entity.User;
import me.hyunggeun.springbootdeveloper.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository blogRepository;
    private final UserRepository userRepository;


    //블로그 글 추가 메서드
    @Transactional(rollbackFor = Exception.class)
    public Article save(AddArticleRequest request) {
        // SecurityContextHolder에서 사용자 정보 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 영속성 컨텍스트에서 User 엔티티 가져오기
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userDetails.getUsername()));

        // 엔티티 변환 및 저장
        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)  // 영속성 컨텍스트에 있는 user 사용
                .build();

        return blogRepository.save(article);
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public List<Article> findByTitleAndContent(String title, String content) {
        return blogRepository.findByTitleAndContent(title, content);
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
