package com.example.hkit.repository;

import com.example.hkit.entity.DirectMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
    //Optional<DirectMessage> findDirectMessageBySender_AccountIDAndContent(String sender, String content);
    //Set<DirectMessage> findDirectMessagesBySender_AccountIDOrReceiver_AccountIDOOrderByDateDesc(String sender, String receiver);
}
