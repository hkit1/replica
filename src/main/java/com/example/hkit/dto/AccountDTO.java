package com.example.hkit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AccountDTO {
    private Long id;
    private String name;
    private String accountID;
    private String accountPW;
    private String email;
    private Boolean hidden;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
