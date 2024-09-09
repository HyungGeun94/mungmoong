package me.hyunggeun.springbootdeveloper.dto;

import lombok.*;
import me.hyunggeun.springbootdeveloper.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentResponse {


    private Long id;
    private String userEmail;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ReplyResponse> replies;




}
