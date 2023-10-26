package com.example.hkit.service;

import com.example.hkit.dto.AccountDTO;
import com.example.hkit.entity.Account;
import com.example.hkit.entity.AccountRelationship;
import com.example.hkit.repository.AccountRelationshipRepository;
import com.example.hkit.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final AccountRepository accountRepository;
    public final AccountRelationshipRepository accountRelationshipRepository;

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

    public void addFollower(Account account, Account target) {
        AccountRelationship accountRelationship = new AccountRelationship();
        accountRelationship.setFollowed(account);
        accountRelationship.setFollower(target);

        accountRelationshipRepository.save(accountRelationship);
    }

    public void removeFollower(Account account, Account target) {
        accountRelationshipRepository.deleteByFollowerIdAndFollowedId(account.getId(), target.getId());
    }
}
