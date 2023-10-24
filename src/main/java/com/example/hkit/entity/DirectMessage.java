package com.example.hkit.entity;


import com.example.hkit.dto.DirectMessageDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "dm")
public class DirectMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender", referencedColumnName = "id")
    private Account sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver", referencedColumnName = "id")
    private Account receiver;

    @Column
    private String content;

    @Column
    private LocalDateTime date;

    public static DirectMessage toEntity(DirectMessageDTO dm) {
        DirectMessage message = new DirectMessage();
        message.setId(dm.getId());
        message.setSender(dm.getSender());
        message.setReceiver(dm.getReceiver());
        message.setContent(dm.getContent());
        message.setDate(dm.getDate());
        return message;
    }
}
