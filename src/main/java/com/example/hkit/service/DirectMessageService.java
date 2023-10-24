package com.example.hkit.service;


import com.example.hkit.dto.DirectMessageDTO;
import com.example.hkit.entity.DirectMessage;
import com.example.hkit.repository.DirectMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectMessageService {
    public final DirectMessageRepository directMessageRepository;

    public void save(DirectMessageDTO messageDTO) {
        DirectMessage account = DirectMessage.toEntity(messageDTO);
        directMessageRepository.save(account);
    }
}
