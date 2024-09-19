package me.hyunggeun.springbootdeveloper.likedislike.repository;

public interface CustomLikeDislikeRepository {

    public int likeCount(Long postId);

    public int dislikeCount(Long postId);

}
