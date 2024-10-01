package me.hyunggeun.springbootdeveloper.likedislike.service;

import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.article.entity.Article;
import me.hyunggeun.springbootdeveloper.article.repository.ArticleRepository;
import me.hyunggeun.springbootdeveloper.likedislike.dto.LikeDislikeResponseDTO;
import me.hyunggeun.springbootdeveloper.likedislike.entity.LikeDislike;
import me.hyunggeun.springbootdeveloper.likedislike.repository.LikeDislikeRepository;
import me.hyunggeun.springbootdeveloper.security.CustomUserDetails;
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




    public LikeDislikeResponseDTO likeDislikeArticle(Long articleId,String action){

        CustomUserDetails principal = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = principal.getUsername();

        Optional<Article> article = articleRepository.findById(articleId);


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<LikeDislike> likeDislike = likeDislikeRepository.findByUserIdAndArticleId(user.getId(), articleId);

        if(likeDislike.isPresent()){

            LikeDislike existingLikeDislike = likeDislike.get();

            if (Boolean.TRUE.equals(existingLikeDislike.getLikeStatus()) && action.equals("like")) {
                likeDislikeRepository.delete(existingLikeDislike);
                action=null;

            }else if (Boolean.FALSE.equals(existingLikeDislike.getLikeStatus()) && action.equals("like")) {

                existingLikeDislike.changeLikeStatus(true); // dirty checking 자동 적용
            }

            else if (Boolean.FALSE.equals(existingLikeDislike.getLikeStatus()) && action.equals("dislike")) {
                likeDislikeRepository.delete(existingLikeDislike);
                action=null;

            } else if (Boolean.TRUE.equals(existingLikeDislike.getLikeStatus()) && action.equals("dislike")) {
                existingLikeDislike.changeLikeStatus(false);
            }

        }  else {
            if (article.isPresent()) {
                LikeDislike newLike = LikeDislike.create(user,article.get() , action.equals("like"));
                likeDislikeRepository.save(newLike);
            }
            else{
                throw new IllegalArgumentException("Article not found");
            }

        }


        // 좋아요 수 등 응답 데이터를 클라이언트로 보냄

        return  new LikeDislikeResponseDTO(action,likeCount(articleId), dislikeCount(articleId) );

    }

    public int likeCount(Long postId){
        return likeDislikeRepository.likeCount(postId);
    }

    public int dislikeCount(Long postId){
        return likeDislikeRepository.dislikeCount(postId);
    }

}
