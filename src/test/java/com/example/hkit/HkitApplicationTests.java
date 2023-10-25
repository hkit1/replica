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
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HkitApplicationTests {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    DirectMessageRepository directMessageRepository;

    @BeforeAll
    public void setup() {
        Account account = new Account();
        account.setAccountID("testid");
        account.setAccountPW("testpw");
        account.setEmail("testMail");
        account.setHidden(false);
        account.setCreated_at(LocalDateTime.now());
        account.setUpdated_at(LocalDateTime.now());

        accountRepository.save(account);

        Account another_account = new Account();
        another_account.setAccountID("testid2");
        another_account.setAccountPW("testpw2");
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

    @Test
    @Transactional
    public void postTest() {
        Post post = postRepository.findAll().get(0);
        assertEquals("contains", post.getContent());
        assertEquals("testMail", post.getAuthor().getEmail());
    }

    @Test
    @Transactional
    public void directMessageTest() {
        DirectMessage dm = directMessageRepository.findAll().get(0);
        assertEquals("direct", dm.getContent());
        assertEquals("testid", dm.getSender().getAccountID());
        assertEquals("testid2", dm.getReceiver().getAccountID());
    }
}
