package me.hyunggeun.springbootdeveloper.likedislike.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hyunggeun.springbootdeveloper.likedislike.dto.LikeDislikeResponseDTO;
import me.hyunggeun.springbootdeveloper.likedislike.service.LikeDislikeService;
import org.springframework.http.ResponseEntity;
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

    /**
     *
     * @param articleId
     * @return
     * 두개 로직 합치기.
     * 어떻게 합치냐면
     * html에서 보내는 Pathvariable에 따라 {like}/{dislike}
     * 일단 보내고 서비스단도 합쳐서 분기처리하기. 그러면 더 깔끔할듯.
     * @PathVariable은 DTO에 담지 못하기 때문에 지금 방법 그대로 유지.
     */

    @PostMapping("/{articleId}/like")
    public ResponseEntity<LikeDislikeResponseDTO> likePost(@PathVariable Long articleId) {

        LikeDislikeResponseDTO likeDislikeResponseDTO = likeDislikeService.likeArticle(articleId);


        return ResponseEntity.ok(likeDislikeResponseDTO);
    }

    // 싫어요 누르기
    @PostMapping("/{articleId}/dislike")
    public ResponseEntity<Map<String, Object>> dislikePost(@PathVariable Long articleId) {

        likeDislikeService.dislikeArticle(articleId);

        // 좋아요 수 등 응답 데이터를 클라이언트로 보냄
        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", likeDislikeService.likeCount(articleId));

        response.put("dislikeCount", likeDislikeService.dislikeCount(articleId));
        response.put("userAction", "dislike");

        return ResponseEntity.ok(response);
    }
}
