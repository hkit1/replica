package com.example.hkit.controller;

import com.example.hkit.dto.AccountDTO;
import com.example.hkit.dto.PostDTO;
import com.example.hkit.entity.Account;
import com.example.hkit.entity.Post;
import com.example.hkit.repository.AccountRepository;
import com.example.hkit.repository.PostRepository;
import com.example.hkit.service.PostService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Transactional
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
     * 글쓰기 폼에서 submit 을 하면 작성 날짜를 추가하고 DB에 쓰기 작업함.
     */
    @PostMapping("/post")
    public ResponseEntity<String> post(@CookieValue(name = "accountId") String accountId, @ModelAttribute PostDTO postDTO) {
        Optional<Account> account = accountRepository.findAccountByAccountID(new String(Base64.getDecoder().decode(accountId.getBytes())));
        JsonObject json = new JsonObject();
        if (account.isEmpty()) {
            json.addProperty("error", postDTO.getAuthorId() + " 계정을 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json.toString());
        } else {
            Post post = Post.toEntity(postDTO);
            post.setAuthor(account.get());
            post.setTime(LocalDateTime.now());
            postService.save(post);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    /**
     * 검색 사이트에서 내용을 입력하고 submit 하면 검색하여 결과를 반환함,
     *
     * @param text 검색 내용
     */
    @ResponseBody
    @PostMapping("/search")
    public String search(@RequestParam(name = "text") String text, @RequestParam(name = "accountId") @Nullable String accountId) {
        // TODO: 2023-10-24 검색 만들기
        JsonArray jsonArray = new JsonArray();
        List<Post> result = postService.findText(text, accountId);
        if (!result.isEmpty()) {
            for (Post post : result) {
                jsonArray.add(PostDTO.toJson(post));
            }

            //서치를 JSON 파일로 바꿔야되는데 일단 저장.
        }
        return jsonArray.toString();
    }

    /**
     * /search/name 으로 계정 ID 검색을 하면 JSON 형태로 반환함.
     *
     * @return JSON 글자로 반환
     */
    @ResponseBody
    @RequestMapping("/search/name")
    //dto
    public String search_name(@RequestParam(name = "accountID") String accountID) {
        JsonArray json = new JsonArray();
        List<Account> searched = accountRepository.findTop5AccountByAccountIDContains(accountID);

        if (!searched.isEmpty()) {
            for (Account account : searched) {
                json.add(AccountDTO.toJson(account));
            }
        }

        // accountid를 account에서 받아와서
        // 그 아이디로 post객체들을 불러와서
        // post를 모델로 하던

        return json.toString();
    }

    @ResponseBody
    @RequestMapping("/load")
    public String loadPost(@RequestParam(name = "lastPage") long lastPage) {
        JsonArray json = new JsonArray();
        List<Post> searched = postRepository.findTop5ItemsAfterId(lastPage, PageRequest.of(0, 5));

        if (!searched.isEmpty()) {
            for (Post post : searched) {
                json.add(PostDTO.toJson(post));
            }
        }

        return json.toString();
    }
}
