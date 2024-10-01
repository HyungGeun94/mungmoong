package me.hyunggeun.springbootdeveloper.article.service;

import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.article.dto.AddArticleRequest;
import me.hyunggeun.springbootdeveloper.article.dto.ArticleResponse;
import me.hyunggeun.springbootdeveloper.article.dto.PageDTO;
import me.hyunggeun.springbootdeveloper.article.entity.Article;
import me.hyunggeun.springbootdeveloper.article.repository.ArticleRepository;
import me.hyunggeun.springbootdeveloper.security.CustomUserDetails;
import me.hyunggeun.springbootdeveloper.user.entity.User;
import me.hyunggeun.springbootdeveloper.user.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
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

        return articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Cacheable(cacheNames = "getBoards", key = "'boards:page:' + #pageable.pageNumber + ':size:' + #pageable.pageSize", cacheManager = "boardCacheManager")
    public PageDTO<ArticleResponse> findByKeyword(String keyword, Pageable pageable) {
        Page<ArticleResponse> articlePage = articleRepository.findByKeyword(keyword, pageable)
                .map(a -> new ArticleResponse(
                        a.getId(),
                        a.getTitle(),
                        a.getContent(),
                        a.getCreatedAt(),
                        a.getUser().getEmail(),
                        0, 0
                ));

        // Page 객체를 PageDTO로 변환하여 반환
        return new PageDTO<>(
                articlePage.getContent(),
                articlePage.getTotalElements()
        );
    }


    public Article findById(Long id) {
        return articleRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("not found" +id)
        );
    }


    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }


    @Transactional
    public Article update(Long id, AddArticleRequest request) {

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Article article = findById(id);

        if(!customUserDetails.getUsername().equals(article.getUser().getEmail())) {

            throw new IllegalArgumentException("User not authorized");

        }


        article.update(request.getTitle(),request.getContent());

        return article;


    }

}
