package com.example.hkit.controller;

import com.example.hkit.dto.AccountDTO;
import com.example.hkit.dto.PostDTO;
import com.example.hkit.entity.Account;
import com.example.hkit.entity.Post;
import com.example.hkit.entity.PostLike;
import com.example.hkit.repository.AccountRepository;
import com.example.hkit.repository.PostLikeRepository;
import com.example.hkit.repository.PostRepository;
import com.example.hkit.service.PostService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Transactional
public class PostController {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostService postService;
    private final AccountRepository accountRepository;

    /**
     * 좋아요 횟수를 계산해서 반환
     * @param post 검색할 포스트 대상
     * @return Post entity 에서 json 으로 변환된 값
     */
    private JsonObject getPost(Post post){
        JsonObject parsed = PostDTO.toJson(post);
        parsed.addProperty("like", postLikeRepository.findAllByPostId(post.getId()).size());
        return PostDTO.toJson(post);
    }

    /**
     * 글쓰기 사이트에서 글쓰기를 누르면 실행됨.
     */
    @GetMapping("/post")
    public String postForm() {
        return "post";
    }

    @GetMapping("/post/{id}")
    public void getPost(@PathVariable String id, Model model) {
        @Getter
        @Setter
        class Postlist {
            Long id;
            String author;
            String content;
            int bookmark_count;
            long like_count;
            String datetime;
        }

        Optional<Post> result = postRepository.findById(Long.valueOf(id));
        if (result.isPresent()) {
            Post r = result.get();
            Postlist l = new Postlist();
            l.id = r.getId();
            l.author = r.getAuthor().getName();
            l.content = r.getContent();
            l.bookmark_count = 0;
            l.like_count = postService.countPostLike(r.getId());
            l.datetime = r.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            model.addAttribute("post", l);
        } else {
            model.addAttribute("post", null);
        }
    }

    /**
     * 글쓰기 폼에서 submit 을 하면 작성 날짜를 추가하고 DB에 쓰기 작업함.
     */
    @PostMapping("`/post`")
    public String post(@CookieValue(name = "accountId") String accountId, @ModelAttribute PostDTO postDTO, Model model) {
        Optional<Account> account = accountRepository.findAccountByAccountID(new String(Base64.getDecoder().decode(accountId.getBytes())));
        if (account.isEmpty()) {
            model.addAttribute("result", "<script>alert(\"" + postDTO.getAuthorId() + " 계정을 찾을 수 없습니다.\");</script>");
        } else {
            Post post = Post.toEntity(postDTO);
            post.setAuthor(account.get());
            post.setTime(LocalDateTime.now());
            postService.save(post);
        }
        return "redirect:/";
    }

    /**
     * 검색 사이트에서 내용을 입력하고 submit 하면 검색하여 결과를 반환함,
     *
     * @param text 검색 내용
     */
    @ResponseBody
    @PostMapping("/search")
    public String search(@RequestParam(name = "text") String text, @RequestParam(name = "accountId") @Nullable String accountId) {
        JsonArray jsonArray = new JsonArray();
        List<Post> result = postService.findText(text, accountId);
        if (!result.isEmpty()) {
            for (Post post : result) {
                JsonObject p = getPost(post);
                if (!p.get("type").getAsString().equals("secret")) {
                    jsonArray.add(getPost(post));
                }
            }
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
                json.add(getPost(post));
            }
        }

        /*JsonArray reverse = new JsonArray();
        for (int i = json.size()-1; i>=0; i--) {
            reverse.add(json.get(i));
        }*/

        return json.toString();
    }

    @ResponseBody
    @GetMapping("/alert")
    public void loadAlert(@CookieValue(name = "accountId") String accountId, Model model) {
        List<Post> found = new ArrayList<>();
        for (Post post : postRepository.findAll()) {
            if (post.getContent().contains(accountId)) {
                found.add(post);
            }
        }
        model.addAttribute("alerts", found);
    }

    /*@ResponseBody
    @PostMapping("/like/{id}")
    public String like(@RequestParam(name = "id") Long id, @CookieValue(name = "accountId") @Nullable String accountId, HttpServletResponse response, Model model) {

    }*/

    // like 좋아요 구현
    @PostMapping("/like")
    public String postLike(@RequestParam(name="id") long id, @CookieValue(name = "accountId") @Nullable String accountId) {
        Set<PostLike> liked = postLikeRepository.findAllByPostId(id);

        for (PostLike post : liked) {
            if (post.getAccount().getAccountID().equals(accountId))
                postLikeRepository.deleteByAccount_AccountID(accountId);
            else {
                PostLike postLike = new PostLike();
                Optional<Post> post1 = postRepository.findById(id);
                postLike.setPost(post1.get());
                Optional<Account> account = accountRepository.findAccountByAccountID(accountId);
                postLike.setAccount(account.get());

                postLikeRepository.save(postLike);
            }
        }

        return "{ like : " + liked + " }";
    }
}