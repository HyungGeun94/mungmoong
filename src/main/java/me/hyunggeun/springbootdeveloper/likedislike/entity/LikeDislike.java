package me.hyunggeun.springbootdeveloper.likedislike.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hyunggeun.springbootdeveloper.article.entity.Article;
import me.hyunggeun.springbootdeveloper.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeDislike {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_dislike_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private Boolean likeStatus; // true: 좋아요, false: 싫어요



    /**
     * 연관관계 편의 메서드 -> user, article 필요시 추가
     **/

    private LikeDislike(User user, Article article, Boolean likeStatus) {
        this.user = user;
        this.article = article;
        this.likeStatus = likeStatus;
    }



    public static LikeDislike create(User user, Article article, Boolean likeStatus) {
        LikeDislike likeDislike = new LikeDislike(user, article, likeStatus);
        return likeDislike;
    }


    public void changeLikeStatus(Boolean status) {
        this.likeStatus = status;
    }


}
