package com.example.hkit.controller;

import com.example.hkit.dto.PostDTO;
import com.example.hkit.entity.Post;
import com.example.hkit.repository.PostRepository;
import com.example.hkit.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

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

    @PostMapping("/search")
    public String search(@RequestParam(name = "text") String text, Model model) {
        // TODO: 2023-10-24 검색 만들기
        Optional<Post> result = postRepository.findAllByTitleContains(text);

        return "search";
    }
}
