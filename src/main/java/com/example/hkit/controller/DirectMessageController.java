package com.example.hkit.controller;

import com.example.hkit.dto.DirectMessageDTO;
import com.example.hkit.entity.Account;
import com.example.hkit.entity.DirectMessage;
import com.example.hkit.repository.AccountRepository;
import com.example.hkit.repository.DirectMessageRepository;
import com.example.hkit.service.DirectMessageService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DirectMessageController {
    private final DirectMessageRepository directMessageRepository;
    private final DirectMessageService directMessageService;
    private final AccountRepository accountRepository;

    @GetMapping("/message")
    public String messageForm() {
        return "index";
    }

    @PostMapping("/message")
    public String message(@ModelAttribute DirectMessageDTO messageDTO) {
        Optional<Account> sender = accountRepository.findAccountByAccountID(messageDTO.getSender());
        Optional<Account> receiver = accountRepository.findAccountByAccountID(messageDTO.getReceiver());
        JsonObject json = new JsonObject();

        if (sender.isEmpty()) {
            json.addProperty("error", messageDTO.getSender() + " 계정을 찾을 수 없습니다.");
        } else if (receiver.isEmpty()) {
            json.addProperty("error", messageDTO.getReceiver() + " 계정을 찾을 수 없습니다.");
        } else {
            DirectMessage directMessage = DirectMessage.toEntity(messageDTO);
            directMessage.setSender(sender.get());
            directMessage.setReceiver(receiver.get());
            directMessage.setDate(LocalDateTime.now());
            directMessageService.save(messageDTO);
        }
        return "index";
    }
}
