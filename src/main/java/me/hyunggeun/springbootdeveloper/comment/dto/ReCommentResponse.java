package me.hyunggeun.springbootdeveloper.comment.dto;

import lombok.*;

import java.time.LocalDateTime;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class ReCommentResponse {

        private Long id;
        private String userEmail;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
