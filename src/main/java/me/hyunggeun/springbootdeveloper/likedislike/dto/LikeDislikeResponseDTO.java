package me.hyunggeun.springbootdeveloper.likedislike.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeDislikeResponseDTO {


    private String userAction;

    private int likeCount;

    private int dislikeCount;

}
