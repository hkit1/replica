package com.example.hkit.entity;

import com.example.hkit.dto.AccountDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "account")
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String accountID;

    @Column
    private String accountPW;

    @Column
    private String email;

    @Column
    private Boolean hidden;

    public static Account toEntity(AccountDTO accountDTO) {
        Account entity = new Account();
        entity.setId(accountDTO.getId());
        entity.setAccountID(accountDTO.getAccountID());
        entity.setAccountPW(accountDTO.getAccountPW());
        entity.setEmail(accountDTO.getEmail());
        entity.setHidden(accountDTO.getHidden());
        return entity;
    }
}
