package me.hyunggeun.springbootdeveloper.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {


    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String writer;
    private int likeCount;
    private int dislikeCount;


}
