package com.example.hkit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "post_relationship")
public class PostRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original")
    private Post original;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply")
    private Post reply;
}
