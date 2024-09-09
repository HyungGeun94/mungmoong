package me.hyunggeun.springbootdeveloper.controller;


import lombok.RequiredArgsConstructor;
import me.hyunggeun.springbootdeveloper.domain.Comment;
import me.hyunggeun.springbootdeveloper.dto.CommentRequest;
import me.hyunggeun.springbootdeveloper.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {


    private final CommentService commentService;


    @PostMapping("/comments")
    public String saveComment(CommentRequest commentRequest) {

        commentService.save(commentRequest);

        return "redirect:/articles/" + commentRequest.getArticleId();

    }
}
