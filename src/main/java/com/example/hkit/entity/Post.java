package com.example.hkit.entity;

import com.example.hkit.dto.PostDTO;
import com.example.hkit.list.PostVisibility;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "id")
    private Account author;

    @Column
    private LocalDateTime time;

    @Column
    private String content;

    @Column
    private PostVisibility type;

    public static Post toEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setAuthor(postDTO.getAuthor());
        post.setTime(postDTO.getTime());
        post.setContent(postDTO.getContent());
        post.setType(postDTO.getType());
        return post;
    }
}