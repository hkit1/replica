package com.example.hkit.repository;

import com.example.hkit.entity.DirectMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
    Optional<DirectMessage> findDirectMessageBySender_AccountIDAndContent(String sender, String content);
}
