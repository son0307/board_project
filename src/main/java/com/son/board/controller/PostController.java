package com.son.board.controller;

import com.son.board.domain.Post;
import com.son.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

//    @GetMapping("/")
//    public String list(Model model) {
//        List<Post> posts = postService.getAllPosts();
//
//        model.addAttribute("posts", posts);
//
//        return "post/index";
//    }
}
