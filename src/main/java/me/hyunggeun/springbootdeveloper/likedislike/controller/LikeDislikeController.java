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

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
@Slf4j
public class LikeDislikeController {

    private final LikeDislikeService likeDislikeService;

    /**
     * @PathVariable은 DTO에 담지 못하기 때문에 지금 방법 그대로 유지.
     */

    @PostMapping("/{articleId}/{action}")
    public ResponseEntity<LikeDislikeResponseDTO> likeDislikePost(
            @PathVariable Long articleId,
            @PathVariable String action) {

        LikeDislikeResponseDTO likeDislikeResponseDTO = likeDislikeService.likeDislikeArticle(articleId,action);


        return ResponseEntity.ok(likeDislikeResponseDTO);
    }

}
