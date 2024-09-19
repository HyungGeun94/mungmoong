package me.hyunggeun.springbootdeveloper.article.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hyunggeun.springbootdeveloper.common.BaseTimeEntity;
import me.hyunggeun.springbootdeveloper.likedislike.entity.LikeDislike;
import me.hyunggeun.springbootdeveloper.user.entity.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id",updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "article")
    Set<LikeDislike> likeDislikes = new HashSet<>();


    @Builder
    public Article(String title, String content,User user) {
        this.title = title;
        this.content = content;
        addUser(user);
    }

    public void addUser(User user){
        this.user = user;
        user.getArticles().add(this);
    }


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
