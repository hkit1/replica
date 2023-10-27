package com.example.hkit.service;

import com.example.hkit.entity.Account;
import com.example.hkit.entity.AccountRelationship;
import com.example.hkit.entity.Post;
import com.example.hkit.entity.PostRelationship;
import com.example.hkit.list.PostVisibility;
import com.example.hkit.repository.AccountRelationshipRepository;
import com.example.hkit.repository.AccountRepository;
import com.example.hkit.repository.PostRelationshipRepository;
import com.example.hkit.repository.PostRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostService {
    public final PostRepository postRepository;
    public final PostRelationshipRepository postRelationshipRepository;
    public final AccountRelationshipRepository accountRelationshipRepository;
    public final AccountRepository accountRepository;

    public void save(Post post) {
        postRepository.save(post);
    }

    public void addReply(Post original, Post reply) {
        PostRelationship postRelationship = new PostRelationship();
        postRelationship.setOriginal(original);
        postRelationship.setReply(reply);

        postRelationshipRepository.save(postRelationship);
    }

    public void removeReply(Post original, Post reply) {
        postRelationshipRepository.deleteByOriginalIdAndReplyId(original.getId(), reply.getId());
    }

    //로그인 상태가 아닐 때 Text 검색한 후 리스트를 가져오는 메서드.
    public List<Post> findText(String text, @Nullable String accountId) {
        List<Post> list = postRepository.findAllByContentContains(text);
        List<Post> result = new ArrayList<>();
        List<AccountRelationship> followed = new ArrayList<>();
        List<Long> idList = new ArrayList<>();
        Account my = new Account();
        if (accountId != null) {
            Optional<Account> a = accountRepository.findAccountByAccountID(accountId);
            if (a.isPresent()) {
                my = a.get();
                followed = accountRelationshipRepository.findAccountRelationshipsByFollowed_Id(my.getId());
            }
        }
        for (AccountRelationship ac : followed) {
            idList.add(ac.getFollowed().getId());
        }

        for (Post post : list) {
            if (post.getType().equals(PostVisibility.open)) {
                result.add(post);
            } else if (accountId != null && post.getType().equals(PostVisibility.hidden) && idList.contains(post.getId())) {
                result.add(post);
            } else if (accountId != null && post.getType().equals(PostVisibility.secret) && post.getAuthor().getId().equals(my.getId())) {
                result.add(post);
            }
        }
        //enum값이 open인걸 확인하고 가져옴
        return result;
    }

}