package com.example.hkit;

import com.example.hkit.entity.*;
import com.example.hkit.list.PostVisibility;
import com.example.hkit.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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
    AccountRelationshipRepository accountRelationshipRepository;
    @Autowired
    PostRelationshipRepository postRelationshipRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    @Transactional
    public void setup() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            Account account = new Account();
            account.setAccountID("testid" + i);
            account.setAccountPW("testpw" + i);
            account.setName("testName" + i);
            account.setEmail("testMail" + i);
            account.setHidden(false);
            account.setCreated_at(LocalDateTime.now());
            account.setUpdated_at(LocalDateTime.now());

            accountRepository.save(account);
        }

        for (int i = 0; i < 10; i++) {
            Post post = new Post();
            post.setAuthor(accountRepository.findAll().get(i));
            post.setTime(LocalDateTime.now());
            post.setContent("contents" + i);
            if(i%3==0) {
                post.setType(PostVisibility.open);
            }else if(i%3==1) {
                post.setType(PostVisibility.hidden);
            }else{
                post.setType(PostVisibility.secret);
            }

            postRepository.save(post);
        }

        for (int i = 0; i < postRepository.findAll().size(); i++) {
            Post post = postRepository.findAll().get(i);
            for (int j = 0; j < 10; j++) {
                Post next = postRepository.findAll().get(j);

                if (!Objects.equals(post.getId(), next.getId())) {
                    PostRelationship postRelationship = new PostRelationship();
                    postRelationship.setOriginal(post);
                    postRelationship.setReply(next);

                    postRelationshipRepository.save(postRelationship);
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            Account a = accountRepository.findAll().get(i);
            for (int j = 0; j < 10; j++) {
                Account b = accountRepository.findAll().get(j);

                DirectMessage directMessage = new DirectMessage();
                directMessage.setSender(a);
                directMessage.setReceiver(b);
                directMessage.setDate(LocalDateTime.now());
                directMessage.setContent("direct" + i);

                directMessageRepository.save(directMessage);
            }
        }

        for (int i = 0; i < 10; i++) {
            Account a = accountRepository.findAll().get(i);
            for (int j = 0; j < 10; j++) {
                Account b = accountRepository.findAll().get(j);
                if (!Objects.equals(a.getId(), b.getId())) {
                    AccountRelationship accountRelationship = new AccountRelationship();
                    accountRelationship.setFollower(a);
                    accountRelationship.setFollowed(b);

                    accountRelationshipRepository.save(accountRelationship);
                }
            }
        }
    }

    /**
     * 글쓰기 기능 테스트
     */
    @Test
    @Transactional
    public void databasePostTest() {
        Post post = postRepository.findAll().get(0);
        assertEquals("contains0", post.getContent());
        assertEquals("testMail", post.getAuthor().getEmail());
    }

    /**
     * 쪽지 기능 테스트
     */
    @Test
    @Transactional
    public void databaseDirectMessageTest() {
        DirectMessage dm = directMessageRepository.findAll().get(0);
        assertEquals("direct0", dm.getContent());
        assertEquals("testid0", dm.getSender().getAccountID());
        assertEquals("testid2", dm.getReceiver().getAccountID());
    }

    /**
     * 클라이언트가 @이름 을 사용할 때 닉네임을 가져오는 테스트
     *
     * @throws Exception 서버 오류 또는 result 값이 같지 않을 경우 발생
     */
    @Test
    public void searchIdFromClientTest() throws Exception {
        String id = "test";
        String result = "[{\"id\":1,\"name\":\"testName0\",\"accountID\":\"testid0\",\"hidden\":false},{\"id\":2,\"name\":\"testName1\",\"accountID\":\"testid1\",\"hidden\":false},{\"id\":3,\"name\":\"testName2\",\"accountID\":\"testid2\",\"hidden\":false},{\"id\":4,\"name\":\"testName3\",\"accountID\":\"testid3\",\"hidden\":false},{\"id\":5,\"name\":\"testName4\",\"accountID\":\"testid4\",\"hidden\":false}]";

        mockMvc.perform(post("/search/name")
                        .param("accountID", id)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(result));
    }

    // TODO: 2023-10-25 Spring security 으로 로그인 작업
    @Test
    public void loginFromClientTest() throws Exception {
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

    /**
     * 계정 팔로잉/팔로워 테스트
     */
    @Test
    @Transactional
    public void databaseFollowTest() {
        AccountRelationship account = accountRelationshipRepository.findAll().get(0);

        assertEquals("testName1", account.getFollowed().getName());
        assertEquals("testName0", account.getFollower().getName());
    }

    /**
     * 답글 테스트
     */
    @Test
    @Transactional
    public void databasePostReplyTest() {
        PostRelationship post = postRelationshipRepository.findAll().get(0);

        assertEquals("contents0", post.getOriginal().getContent());
        assertEquals("contents1", post.getReply().getContent());
    }

    /**
     * 포스트를 검색했을 때 공개 설정에 따라 표시 되는지 확인
     *
     * @throws Exception 서버 오류 또는 result 값이 같지 않을 경우 발생
     */
    @Test
    public void searchPostTest() throws Exception {
        String result = "[{\"author\":\"testName0\",\"content\":\"contents0\",\"like\":0,\"bookmark\":0,\"type\":\"open\"},{\"author\":\"testName2\",\"content\":\"contents2\",\"like\":0,\"bookmark\":0,\"type\":\"secret\"},{\"author\":\"testName3\",\"content\":\"contents3\",\"like\":0,\"bookmark\":0,\"type\":\"open\"},{\"author\":\"testName6\",\"content\":\"contents6\",\"like\":0,\"bookmark\":0,\"type\":\"open\"},{\"author\":\"testName9\",\"content\":\"contents9\",\"like\":0,\"bookmark\":0,\"type\":\"open\"}]";

        mockMvc.perform(post("/search")
                        .param("text", "content")
                        .param("accountId", "testid2")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(result));
    }

    @Test
    public void updateAccountFromClientTest() throws Exception {
        mockMvc.perform(post("/update")
                        .param("name", "testName0-edited")
                        .param("accountID", "testid0")
                        .param("accountPW", "testpw0-edited")
                        .param("email", "testMail0-edited")
                        .param("hidden", "false")
                )
                .andExpect(status().isOk());

        Optional<Account> account = accountRepository.findAccountByAccountID("testid0");
        if (account.isPresent()) {
            Account me = account.get();
            assertEquals("testName0-edited", me.getName());
            assertEquals("testid0", me.getAccountID());
            assertEquals("testpw0-edited", me.getAccountPW());
            assertEquals("testMail0-edited", me.getEmail());
            assertEquals(false, me.getHidden());
        } else {
            fail();
        }
    }

    @Test
    @Transactional
    public void postFromClientTest() throws Exception {
        mockMvc.perform(post("/post")
                        .param("authorId", "testid1")
                        .param("content", "too-much-texts")
                        .param("type", "hidden")
                )
                .andExpect(status().isOk());

        Optional<Post> post = postRepository.findPostByAuthor_AccountIDAndContent("testid1", "too-much-texts");
        if (post.isPresent()) {
            Post me = post.get();
            assertEquals("testName1", me.getAuthor().getName());
            assertEquals("too-much-texts", me.getContent());
            assertEquals(PostVisibility.hidden, me.getType());
            assertEquals(LocalDateTime.now().getDayOfWeek(), me.getTime().getDayOfWeek());
        } else {
            fail();
        }
    }

    @Test
    public void sendDirectMessageFromClientTest() throws Exception {

    }
}
