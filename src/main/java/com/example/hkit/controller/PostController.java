package com.example.hkit.controller;

import com.example.hkit.dto.AccountDTO;
import com.example.hkit.dto.PostDTO;
import com.example.hkit.entity.Account;
import com.example.hkit.entity.Post;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

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

    /**
     * 글쓰기 폼에서 submit 을 하면 작성 날짜를 추가하고 DB에 쓰기 작업함.
     */
    @PostMapping("/post")
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
    @GetMapping("/index")
    public String loadPost(@RequestParam(name = "accountID") @Nullable String accountID, Model model) {
        List<Post> list = postService.findAll(accountID);
        List<Postlist> postlists = new ArrayList<>();

        for(Post post : list){
            Postlist postlist = new Postlist();
            postlist.setAuthor(post.getAuthor().getName());
            postlist.setContent(post.getContent());
            postlist.setBookmark(0);//일단 0으로 해놓음
            postlist.setLike_count(postService.countPostLike(post.getId()));//일단 0으로 해놓음
        }
        model.addAttribute("postlist" ,postlists);



        /*JsonArray reverse = new JsonArray();
        for (int i = json.size()-1; i>=0; i--) {
            reverse.add(json.get(i));
        }*/

        return "index";
    }

    /*@ResponseBody
    @PostMapping("/like/{id}")
    public String like(@RequestParam(name = "id") Long id, @CookieValue(name = "accountId") @Nullable String accountId, HttpServletResponse response, Model model) {

    }*/

    @Getter
    @Setter
    class Postlist{
        String author;
        String content;
        int bookmark;
        long like_count;


    }
}
