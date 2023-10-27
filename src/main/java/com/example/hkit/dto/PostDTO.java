package com.example.hkit.dto;


import com.example.hkit.entity.Account;
import com.example.hkit.entity.Post;
import com.example.hkit.list.PostVisibility;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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

    public static PostDTO toDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setAuthor(post.getAuthor());
        postDTO.setTime(post.getTime());
        postDTO.setContent(post.getContent());
        postDTO.setType(post.getType());
        return postDTO;
    }

    public static JsonObject toJson(Post post) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("author", post.getAuthor().getName());
        jsonObject.addProperty("content", post.getContent());
        jsonObject.addProperty("like", 0);
        jsonObject.addProperty("bookmark", 0);
        jsonObject.addProperty("type", post.getType().toString());
        return jsonObject;
    }


}
