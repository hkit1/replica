package com.example.hkit.controller;

import com.example.hkit.dto.AccountDTO;
import com.example.hkit.dto.PostDTO;
import com.example.hkit.entity.Account;
import com.example.hkit.entity.Post;
import com.example.hkit.repository.AccountRepository;
import com.example.hkit.repository.PostRepository;
import com.example.hkit.service.PostService;
import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;
    private final AccountRepository accountRepository;

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
    public String search(@RequestParam(name = "text") String text) {
        // TODO: 2023-10-24 검색 만들기
        List<Post> result = postRepository.findAllByContentContains(text);

        return "search";
    }

    /**
     * /search/name 으로 계정 ID 검색을 하면 JSON 형태로 반환함.
     *
     * @return JSON 글자로 반환
     */
    @ResponseBody
    @RequestMapping("/search/name")
    public String search_name(@RequestParam(name = "accountID") String accountID) {
        JsonArray json = new JsonArray();
        List<Account> searched = accountRepository.findTop5AccountByAccountIDContains(accountID);

        if (!searched.isEmpty()) {
            for (Account account : searched) {
                json.add(AccountDTO.toJson(account));
            }
        }

        return json.toString();
    }

    @PostMapping("/load")
    public void loadPost(@RequestParam(name = "lastPage") long lastPage, Model model) {
        List<Post> result = postRepository.findTop5ItemsAfterId(lastPage, PageRequest.of(0, 5));
        model.addAttribute(result);
    }
}
