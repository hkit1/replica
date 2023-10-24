package com.example.hkit.controller;

import com.example.hkit.dto.DirectMessageDTO;
import com.example.hkit.repository.DirectMessageRepository;
import com.example.hkit.service.DirectMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class DirectMessageController {
    private final DirectMessageRepository directMessageRepository;
    private final DirectMessageService directMessageService;

    @GetMapping("/message")
    public String messageForm() {
        return "message";
    }

    @PostMapping("/message")
    public String message(@ModelAttribute DirectMessageDTO messageDTO) {
        directMessageService.save(messageDTO);
        return "message";
    }
}
