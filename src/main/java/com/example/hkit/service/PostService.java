package com.example.hkit.service;

import com.example.hkit.dto.PostDTO;
import com.example.hkit.entity.Post;
import com.example.hkit.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    public final PostRepository postRepository;

    public void save(PostDTO postDTO) {
        Post post = Post.toEntity(postDTO);
        postRepository.save(post);
    }
}