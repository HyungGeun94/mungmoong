package me.hyunggeun.springbootdeveloper.likedislike.repository;

import me.hyunggeun.springbootdeveloper.likedislike.entity.LikeDislike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeDislikeRepository extends CustomLikeDislikeRepository,JpaRepository<LikeDislike,Long> {

    Optional<LikeDislike> findByUserIdAndArticleId(Long id, Long articleId);



}
