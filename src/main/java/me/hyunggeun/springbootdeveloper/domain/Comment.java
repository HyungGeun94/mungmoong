package me.hyunggeun.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> replies = new ArrayList<>();

    @Builder
    public Comment(User user, Article article, String content, Comment parent) {
        this.user = user;
        this.article = article;
        this.content = content;
        if(parent != null) {
            updateParent(parent);
        }
    }

    private void updateParent(Comment parent) {
        this.parent = parent;
        parent.getReplies().add(this);
    }
}