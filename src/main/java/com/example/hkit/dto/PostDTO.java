package com.example.hkit.dto;


import com.example.hkit.entity.Post;
import com.example.hkit.repository.PostRepository;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostDTO {
    private String authorId;
    private String content;
    private String type;

    public static PostDTO toDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setContent(post.getContent());
        postDTO.setType(post.getType().toString());
        return postDTO;
    }

    public static JsonObject toJson(Post post) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("author", post.getAuthor().getName());
        jsonObject.addProperty("content", post.getContent());
        jsonObject.addProperty("bookmark", 0);
        jsonObject.addProperty("type", post.getType().toString());
        return jsonObject;
    }
}
