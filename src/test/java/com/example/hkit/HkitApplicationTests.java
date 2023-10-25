package com.example.hkit;

import com.example.hkit.entity.Account;
import com.example.hkit.entity.DirectMessage;
import com.example.hkit.entity.Post;
import com.example.hkit.list.PostVisibility;
import com.example.hkit.repository.AccountRepository;
import com.example.hkit.repository.DirectMessageRepository;
import com.example.hkit.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class HkitApplicationTests {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    DirectMessageRepository directMessageRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        Account account = new Account();
        account.setAccountID("testid");
        account.setAccountPW("testpw");
        account.setName("testName1");
        account.setEmail("testMail");
        account.setHidden(false);
        account.setCreated_at(LocalDateTime.now());
        account.setUpdated_at(LocalDateTime.now());

        accountRepository.save(account);

        Account another_account = new Account();
        another_account.setAccountID("testid2");
        another_account.setAccountPW("testpw2");
        another_account.setName("testName2");
        another_account.setEmail("testMail2");
        another_account.setHidden(false);
        another_account.setCreated_at(LocalDateTime.now());
        another_account.setUpdated_at(LocalDateTime.now());

        accountRepository.save(another_account);

        Post post = new Post();
        post.setAuthor(account);
        post.setTime(LocalDateTime.now());
        post.setContent("contents");
        post.setType(PostVisibility.open);

        postRepository.save(post);

        DirectMessage directMessage = new DirectMessage();
        directMessage.setSender(account);
        directMessage.setReceiver(another_account);
        directMessage.setDate(LocalDateTime.now());
        directMessage.setContent("direct");

        directMessageRepository.save(directMessage);
    }

    /**
     * 글쓰기 기능 테스트
     */
    @Test
    @Transactional
    public void postTest() {
        Post post = postRepository.findAll().get(0);
        assertEquals("contains", post.getContent());
        assertEquals("testMail", post.getAuthor().getEmail());
    }

    /**
     * 쪽지 기능 테스트
     */
    @Test
    @Transactional
    public void directMessageTest() {
        DirectMessage dm = directMessageRepository.findAll().get(0);
        assertEquals("direct", dm.getContent());
        assertEquals("testid", dm.getSender().getAccountID());
        assertEquals("testid2", dm.getReceiver().getAccountID());
    }

    /**
     * 클라이언트가 @이름 을 사용할 때 닉네임을 가져오는 테스트
     *
     * @throws Exception 서버 오류 또는 result 값이 같지 않을 경우 발생
     */
    @Test
    public void searchIdTest() throws Exception {
        String id = "test";
        String result = "[{\"id\":1,\"name\":\"testName1\",\"accountID\":\"testid\",\"hidden\":false},{\"id\":2,\"name\":\"testName2\",\"accountID\":\"testid2\",\"hidden\":false}]";

        mockMvc.perform(post("/search/name")
                        .param("accountID", id)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(result));
    }

    // TODO: 2023-10-25 Spring security 으로 로그인 작업
    @Test
    public void loginTest() throws Exception {
        String id = "testid";
        String pw = "testpw";
        String result = "";

        mockMvc.perform(post("/login")
                        .param("accountID", id)
                        .param("accountPW", pw)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(result));
    }
}
