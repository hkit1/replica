package com.example.hkit.service;

import com.example.hkit.dto.PostDTO;
import com.example.hkit.entity.Post;
import com.example.hkit.entity.PostRelationship;
import com.example.hkit.repository.PostRelationshipRepository;
import com.example.hkit.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    public final PostRepository postRepository;
    public final PostRelationshipRepository postRelationshipRepository;

    public void save(PostDTO postDTO) {
        Post post = Post.toEntity(postDTO);
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
}