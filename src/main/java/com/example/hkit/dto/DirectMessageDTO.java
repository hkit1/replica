package com.example.hkit.dto;

import com.example.hkit.entity.Account;
import com.example.hkit.entity.DirectMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DirectMessageDTO {
    private Long id;
    private Account sender;
    private Account receiver;
    private String content;
    private LocalDateTime date;

    public static DirectMessageDTO toDTO(DirectMessage dm) {
        DirectMessageDTO directMessageDTO = new DirectMessageDTO();
        directMessageDTO.setId(dm.getId());
        directMessageDTO.setSender(dm.getSender());
        directMessageDTO.setReceiver(dm.getReceiver());
        directMessageDTO.setContent(dm.getContent());
        directMessageDTO.setDate(dm.getDate());
        return directMessageDTO;
    }
}
