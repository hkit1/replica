package com.example.hkit.dto;


import com.example.hkit.entity.Account;
import com.example.hkit.list.PostVisibility;
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
}
