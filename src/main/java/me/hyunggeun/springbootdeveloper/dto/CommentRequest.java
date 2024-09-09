package me.hyunggeun.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequest {
    private String content;
    private Long articleId;
    private Long parentId;

}