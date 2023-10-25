package com.example.hkit.entity;

import com.example.hkit.dto.AccountDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "account")
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String accountID;

    @Column
    private String accountPW;

    @Column
    private String email;

    @Column
    private Boolean hidden;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @ManyToMany
    @JoinTable(name = "followers", joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "follower_of_id"))
    private Set<Account> followers;

    @ManyToMany
    @JoinTable(name = "following", joinColumns = @JoinColumn(name = "following_id"), inverseJoinColumns = @JoinColumn(name = "following_of_id"))
    private Set<Account> following;

    public static Account toEntity(AccountDTO accountDTO) {
        Account entity = new Account();
        entity.setId(accountDTO.getId());
        entity.setName(accountDTO.getName());
        entity.setAccountID(accountDTO.getAccountID());
        entity.setAccountPW(accountDTO.getAccountPW());
        entity.setEmail(accountDTO.getEmail());
        entity.setHidden(accountDTO.getHidden());
        entity.setFollowers(accountDTO.getFollowers());
        entity.setFollowing(accountDTO.getFollowing());
        return entity;
    }
}
