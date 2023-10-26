package com.example.hkit.dto;


import com.example.hkit.entity.Account;
import com.example.hkit.entity.Post;
import com.example.hkit.list.PostVisibility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostDTO {
    private Long id;
    private Account author;
    private LocalDateTime time;
    private String content;
    private PostVisibility type;
    private Set<Post> reply;

    public static PostDTO toDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setAuthor(post.getAuthor());
        postDTO.setTime(post.getTime());
        postDTO.setContent(post.getContent());
        postDTO.setType(post.getType());
        return postDTO;
    }
}
