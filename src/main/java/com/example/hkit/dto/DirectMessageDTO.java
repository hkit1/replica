package com.example.hkit.dto;

import com.example.hkit.entity.DirectMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DirectMessageDTO {
    private String sender;
    private String receiver;
    private String content;

    public static DirectMessageDTO toDTO(DirectMessage dm) {
        DirectMessageDTO directMessageDTO = new DirectMessageDTO();
        directMessageDTO.setSender(dm.getSender().getAccountID());
        directMessageDTO.setReceiver(dm.getReceiver().getAccountID());
        directMessageDTO.setContent(dm.getContent());
        return directMessageDTO;
    }
}
