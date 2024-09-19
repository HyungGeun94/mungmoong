package me.hyunggeun.springbootdeveloper.likedislike.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hyunggeun.springbootdeveloper.likedislike.service.LikeDislikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
@Slf4j
public class LikeDislikeController {

    private final LikeDislikeService likeDislikeService;

    // 좋아요 누르기
    @PostMapping("/{articleId}/like")
    public ResponseEntity<Map<String, Object>> likePost(@PathVariable Long articleId) {

        log.warn("여기까진 왔습니다");
        System.out.println("여기까진");


        // SecurityContextHolder에서 현재 사용자 정보 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        likeDislikeService.likeArticle(username, articleId);

        // 좋아요 수 등 응답 데이터를 클라이언트로 보냄
        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", likeDislikeService.likeCount(articleId));
        response.put("dislikeCount", likeDislikeService.dislikeCount(articleId));
        response.put("userAction", "like");

        return ResponseEntity.ok(response);
    }

    // 싫어요 누르기
    @PostMapping("/{articleId}/dislike")
    public ResponseEntity<Map<String, Object>> dislikePost(@PathVariable Long articleId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        likeDislikeService.dislikeArticle(username, articleId);

        // 좋아요 수 등 응답 데이터를 클라이언트로 보냄
        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", likeDislikeService.likeCount(articleId));

        response.put("dislikeCount", likeDislikeService.dislikeCount(articleId));
        response.put("userAction", "dislike");

        return ResponseEntity.ok(response);
    }
}
