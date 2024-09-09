package me.hyunggeun.springbootdeveloper.dto;

import lombok.*;

import java.time.LocalDateTime;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class ReplyResponse {

        private Long id;
        private String userEmail;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
