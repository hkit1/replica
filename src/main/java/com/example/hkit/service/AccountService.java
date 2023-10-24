package com.example.hkit.service;

import com.example.hkit.dto.AccountDTO;
import com.example.hkit.entity.Account;
import com.example.hkit.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final AccountRepository accountRepository;

    public void save(AccountDTO accountDTO) {
        Account account = Account.toEntity(accountDTO);
        accountRepository.save(account);
    }
}
