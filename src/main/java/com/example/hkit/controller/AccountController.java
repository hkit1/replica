package com.example.hkit.controller;

import com.example.hkit.dto.AccountDTO;
import com.example.hkit.entity.Account;
import com.example.hkit.repository.AccountRepository;
import com.example.hkit.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        accountService.save(accountDTO);
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
     * @return 성공시 index, 실패시 login.
     */
    @PostMapping("/login")
    public String login(@ModelAttribute Account account, HttpSession session) {
        AccountDTO result = accountService.login(account);
        if (result != null) {
            session.setAttribute("loginId", result);
            return "index";
        } else {
            return "login";
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
    public String update(@ModelAttribute AccountDTO accountDTO) {
        Account account = accountRepository.getReferenceById(accountDTO.getId());
        account.setName(accountDTO.getName());
        account.setAccountID(accountDTO.getAccountID());
        account.setAccountPW(accountDTO.getAccountPW());
        account.setEmail(accountDTO.getEmail());
        account.setHidden(accountDTO.getHidden());
        accountRepository.save(account);
        return "settings";
    }
}
