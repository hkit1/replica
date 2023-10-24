package com.example.hkit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
}
