package com.example.hkit.service;

import com.example.hkit.dto.AccountDTO;
import com.example.hkit.entity.Account;
import com.example.hkit.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final AccountRepository accountRepository;

    public void save(AccountDTO accountDTO) {
        Account account = Account.toEntity(accountDTO);
        accountRepository.save(account);
    }

    public AccountDTO login(Account account) {
        Optional<Account> byId = accountRepository.findAccountByAccountID(account.getAccountID());
        if (byId.isPresent()) {
            Account acc = byId.get();
            if (acc.getAccountPW().equals(account.getAccountPW())) {
                return AccountDTO.toDTO(acc);
            }
        }
        return null;
    }

    @Transactional
    public Account follow(Account follower, Account target) {
        follower.getFollowing().add(target);
        target.getFollowers().add(follower);
        accountRepository.save(follower);
        accountRepository.save(target);
        return follower;
    }

    @Transactional
    public Account unfollow(Account follower, Account target) {
        follower.getFollowing().remove(target);
        target.getFollowers().remove(follower);
        accountRepository.save(follower);
        accountRepository.save(target);
        return follower;
    }

    public Set<Account> getFollowers(Account account) {
        return account.getFollowers();
    }

    public Set<Account> getFollowing(Account account) {
        return account.getFollowing();
    }
}
