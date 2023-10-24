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

    /**
     * 글쓰기 사이트에서 글쓰기를 누르면 실행됨.
     */
    @GetMapping("/post")
    public String postForm() {
        return "post";
    }

    /**
     * 글쓰기 폼에서 submit 을 하면 DB에 쓰기 작업함.
     */
    @PostMapping("/post")
    public String post(@ModelAttribute PostDTO postDTO) {
        postService.save(postDTO);
        return "index";
    }

    /**
     * 검색 사이트에서 내용을 입력하고 submit 하면 검색하여 결과를 반환함,
     *
     * @param text 검색 내용
     */
    @PostMapping("/search")
    public String search(@RequestParam(name = "text") String text, Model model) {
        // TODO: 2023-10-24 검색 만들기
        Optional<Post> result = postRepository.findAllByTitleContains(text);

        return "search";
    }
}
