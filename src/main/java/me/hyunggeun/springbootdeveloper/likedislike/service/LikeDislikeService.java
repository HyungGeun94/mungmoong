package me.hyunggeun.springbootdeveloper.likedislike.service;

import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.article.entity.Article;
import me.hyunggeun.springbootdeveloper.article.repository.ArticleRepository;
import me.hyunggeun.springbootdeveloper.likedislike.dto.LikeDislikeResponseDTO;
import me.hyunggeun.springbootdeveloper.likedislike.entity.LikeDislike;
import me.hyunggeun.springbootdeveloper.likedislike.repository.LikeDislikeRepository;
import me.hyunggeun.springbootdeveloper.user.entity.User;
import me.hyunggeun.springbootdeveloper.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeDislikeService {

    private final LikeDislikeRepository likeDislikeRepository;

    private final UserRepository userRepository;

    private final ArticleRepository articleRepository;

    public int likeCount(Long postId){
        return likeDislikeRepository.likeCount(postId);
    }

    public int dislikeCount(Long postId){
        return likeDislikeRepository.dislikeCount(postId);
    }



    public LikeDislikeResponseDTO likeArticle(Long articleId) {

        // SecurityContextHolder에서 현재 사용자 정보 가져오기
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // username으로 User 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<LikeDislike> likeDislike = likeDislikeRepository.findByUserIdAndArticleId(user.getId(), articleId);

        if (likeDislike.isPresent()) {

            LikeDislike existingLike = likeDislike.get();

            if (Boolean.TRUE.equals(existingLike.getLikeStatus())) {
                likeDislikeRepository.delete(existingLike);

            } else {

                existingLike.changeLikeStatus(true); // dirty checking 자동 적용
            }
        }
        else {
            Optional<Article> article = articleRepository.findById(articleId);
            if (article.isPresent()) {
                LikeDislike newLike = LikeDislike.create(user,article.get() ,true);
                likeDislikeRepository.save(newLike);
            }
            else{
                throw new IllegalArgumentException("Article not found");
            }
        }

        // 좋아요 수 등 응답 데이터를 클라이언트로 보냄

        LikeDislikeResponseDTO likeDislikeResponseDTO = new LikeDislikeResponseDTO("like",likeCount(articleId), dislikeCount(articleId) );


        return likeDislikeResponseDTO;
    }

    public void dislikeArticle(Long articleId) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<LikeDislike> likeDislike = likeDislikeRepository.findByUserIdAndArticleId(user.getId(), articleId);

        if (likeDislike.isPresent()) {
            LikeDislike existingLike = likeDislike.get();
            if (Boolean.FALSE.equals(existingLike.getLikeStatus())) {
                likeDislikeRepository.delete(existingLike);
            } else {
                existingLike.changeLikeStatus(false);
            }
        } else {
            LikeDislike newLike = LikeDislike.create(user, articleRepository.findById(articleId).get(), false);
//            LikeDislike newLike = new LikeDislike();
//            newLike.setUser(user);
//            newLike.setPost(new Post(postId));
//            newLike.setLikeStatus(true);
            likeDislikeRepository.save(newLike);
//            LikeDislike newLike = new LikeDislike();
//            newLike.setUser(user);
//            newLike.setPost(new Post(postId));
//            newLike.setLikeStatus(false);
//            likeDislikeRepository.save(newLike);
        }
    }
}
