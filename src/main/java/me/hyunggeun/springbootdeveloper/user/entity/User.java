package me.hyunggeun.springbootdeveloper.user.entity;


import jakarta.persistence.*;
import lombok.*;
import me.hyunggeun.springbootdeveloper.article.entity.Article;
import me.hyunggeun.springbootdeveloper.common.entity.BaseTimeEntity;
import me.hyunggeun.springbootdeveloper.likedislike.entity.LikeDislike;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;


    @OneToMany(mappedBy = "user")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Set<LikeDislike> likeDislikes = new HashSet<>();

    @Builder
    public User(String email, String password, RoleType role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
