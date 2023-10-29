package com.example.hkit.controller;

import com.example.hkit.dto.AccountDTO;
import com.example.hkit.entity.Account;
import com.example.hkit.repository.AccountRepository;
import com.example.hkit.service.AccountService;
import com.google.gson.JsonObject;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @GetMapping("/")
    public String getLoginData(@CookieValue(name = "accountId") @Nullable String accountId, HttpServletResponse response, Model model) {
        if (accountId != null) {
            Optional<Account> result = accountRepository.findAccountByAccountID(new String(Base64.getDecoder().decode(accountId)));
            if (result.isPresent()) {
                model.addAttribute("login", result.get().getName());
            } else {
                Cookie cookie = new Cookie("accountId", null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                model.addAttribute("login", "<a href=\"/login\">로그인</a>");
            }
        } else {
            model.addAttribute("login", "<a href=\"/login\">로그인</a>");
        }

        return "index";
    }

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
    public ResponseEntity<String> register(@ModelAttribute AccountDTO accountDTO) {
        if (!accountService.checkExists(accountDTO.getAccountID())) {
            Account account = Account.toEntity(accountDTO);
            account.setHidden(false);
            account.setCreated_at(LocalDateTime.now());
            account.setUpdated_at(LocalDateTime.now());
            accountService.save(account);
            return ResponseEntity.status(HttpStatus.OK).body("<script>alert(\"회원 가입이 되었습니다! 로그인을 해 주세요.\");</script>");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("<script>alert(\"이미 있는 ID 입니다!\");</script>");

        }
    }

    /**
     * 로그인 사이트에서 정보를 입력하고 submit 하면 실행됨.
     */
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("result", "로그인");
        return "login";
    }

    /**
     * 로그인 폼에서 submit 을 하면 DB에서 확인함
     *
     * @return 성공시 redirect, 실패시 login.
     */
    @PostMapping("/login")
    public String login(@ModelAttribute Account account, HttpServletResponse response, Model model) {
        AccountDTO result = accountService.login(account);
        if (result != null) {
            response.addCookie(new Cookie("accountId", Base64.getEncoder().encodeToString(account.getAccountID().getBytes())));
            model.addAttribute("login", result.getName());
            return "index";
        } else {
            model.addAttribute("result", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login";
        }
    }

    /**
     * 브라우저에서 전송한 쿠키 값을 서버에서 확인함.
     *
     * @param accountId Base64 으로 인코딩 된 계정 ID값
     * @return 유효한 값인 경우 OK, 서버에서 찾을 수 없는 경우 FORBIDDEN
     */
    @GetMapping("/write")
    public ResponseEntity<String> checkLogin(@CookieValue(value = "accountId") String accountId, HttpServletResponse response) {
        Optional<Account> result = accountRepository.findAccountByAccountID(new String(Base64.getDecoder().decode(accountId)));
        if (result.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            // 유효하지 않은 쿠키를 삭제함
            Cookie cookie = new Cookie("accountId", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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
