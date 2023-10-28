package com.example.hkit.controller;

import com.example.hkit.dto.AccountDTO;
import com.example.hkit.entity.Account;
import com.example.hkit.repository.AccountRepository;
import com.example.hkit.service.AccountService;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    /**
     * 회원가입 사이트에서 정보를 입력하고 submit 하면 실행됨.
     */
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    /**
     * 회원가입 폼에서 submit 을 하면 DB에 등록함.
     */
    @PostMapping("/register")
    public String register(@ModelAttribute AccountDTO accountDTO) {
        Account account = Account.toEntity(accountDTO);
        account.setHidden(false);
        account.setCreated_at(LocalDateTime.now());
        account.setUpdated_at(LocalDateTime.now());
        accountService.save(account);
        return "index";
    }

    /**
     * 로그인 사이트에서 정보를 입력하고 submit 하면 실행됨.
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * 로그인 폼에서 submit 을 하면 DB에서 확인함
     *
     * @return 성공시 redirect, 실패시 login.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@ModelAttribute Account account, HttpSession session) {
        AccountDTO result = accountService.login(account);
        JsonObject json = new JsonObject();
        if (result != null) {
            json.addProperty("accountId", result.getAccountID());
            json.addProperty("name", result.getName());
            json.addProperty("email", result.getEmail());
            json.addProperty("hidden", result.getHidden());
            return ResponseEntity.status(HttpStatus.OK).body(json.toString());
        } else {
            json.addProperty("result", "failed");
            return ResponseEntity.status(HttpStatus.OK).body(json.toString());
        }
    }

    /**
     * 설정 사이트로 이동할 때 사용됨.
     */
    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }

    /**
     * 계정 데이터를 업데이트 할 때 사용됨.
     *
     * @return settings 으로 이동함.
     */
    @PostMapping("/update")
    public ResponseEntity<String> update(@ModelAttribute AccountDTO accountDTO) {
        Optional<Account> account = accountRepository.findAccountByAccountID(accountDTO.getAccountID());
        if (account.isEmpty()) {
            JsonObject json = new JsonObject();
            json.addProperty("error", accountDTO.getAccountID() + " 계정을 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json.toString());
        } else {
            Account me = account.get();
            me.setName(accountDTO.getName());
            me.setAccountID(accountDTO.getAccountID());
            me.setAccountPW(accountDTO.getAccountPW());
            me.setEmail(accountDTO.getEmail());
            me.setHidden(accountDTO.getHidden());
            accountRepository.save(me);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
