package com.example.hkit.service;

import com.example.hkit.dto.PostDTO;
import com.example.hkit.entity.Account;
import com.example.hkit.entity.Post;
import com.example.hkit.repository.AccountRepository;
import com.example.hkit.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    public final AccountRepository accountRepository;
    public final PostRepository postRepository;

    public void save(PostDTO postDTO) {
        Post post = Post.toEntity(postDTO);
        postRepository.save(post);
    }

    public List<Account> findByAccount(String accountID){
        List<Account> searched = accountRepository.findTop5AccountByAccountIDContains(accountID);
    }
}