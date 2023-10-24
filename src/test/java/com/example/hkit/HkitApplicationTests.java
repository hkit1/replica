package com.example.hkit;

import com.example.hkit.entity.Account;
import com.example.hkit.entity.Post;
import com.example.hkit.list.PostVisibility;
import com.example.hkit.repository.AccountRepository;
import com.example.hkit.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HkitApplicationTests {
    AccountRepository accountRepository;
    PostRepository postRepository;

    @BeforeAll
    public void setup() {
        Account account = new Account();
        account.setAccountPW("testid");
        account.setAccountPW("testpw");
        account.setEmail("testMail");
        account.setHidden(false);
        account.setCreated_at(LocalDateTime.now());
        account.setUpdated_at(LocalDateTime.now());

        accountRepository.save(account);

        Post post = new Post();
        post.setAuthor(account);
        post.setTime(LocalDateTime.now());
        post.setContent("contents");
        post.setType(PostVisibility.open);

        postRepository.save(post);
    }

    @Test
    @Transactional
    public void writeTest() {
        Post post = postRepository.findAll().get(0);
        assert ("contents".equals(post.getContent()));
        assert ("testMail".equals(post.getAuthor().getEmail()));
    }
}
