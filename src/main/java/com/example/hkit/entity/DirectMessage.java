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
    @JoinColumn(name = "sender")
    private Account sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver")
    private Account receiver;

    @Column
    private String content;

    @Column
    private LocalDateTime date;

    public static DirectMessage toEntity(DirectMessageDTO dm) {
        DirectMessage message = new DirectMessage();
        message.setContent(dm.getContent());
        return message;
    }
}
