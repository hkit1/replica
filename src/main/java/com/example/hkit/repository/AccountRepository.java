package com.example.hkit.repository;

import com.example.hkit.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByAccountID(String accountID);

    List<Account> findTop5AccountByAccountIDContains(String text);
}
