package com.example.hkit.service;

import com.example.hkit.dto.PostDTO;
import com.example.hkit.entity.Post;
import com.example.hkit.entity.PostRelationship;
import com.example.hkit.list.PostVisibility;
import com.example.hkit.repository.PostRelationshipRepository;
import com.example.hkit.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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

    //로그인 상태가 아닐 때 Text 검색한 후 리스트를 가져오는 메서드.
    public List<Post> findText(String text){
        List<Post> list = postRepository.findAllByContentContains(text);
        List<Post> result= new ArrayList<Post>();
        if(list.isEmpty()){
            return null;
        }
        for(Post post:list){
            if(post.getType().equals(PostVisibility.open)){
                result.add(post);
            }
        }
        // enum값이 open인걸 확인하고 가져옴
        return result;
    }
}