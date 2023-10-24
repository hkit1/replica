package com.example.hkit.dto;

import com.example.hkit.entity.Account;
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

    public static AccountDTO toDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setName(account.getName());
        accountDTO.setAccountPW(accountDTO.getAccountPW());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setHidden(account.getHidden());
        accountDTO.setCreated_at(account.getCreated_at());
        accountDTO.setUpdated_at(account.getUpdated_at());

        return accountDTO;
    }
}
