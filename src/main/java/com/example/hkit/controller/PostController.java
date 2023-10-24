package com.example.hkit.controller;

import com.example.hkit.dto.PostDTO;
import com.example.hkit.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/post")
    public String postForm() {
        return "post";
    }

    @PostMapping("/post")
    public String post(@ModelAttribute PostDTO postDTO) {
        postService.save(postDTO);
        return "index";
    }
}
