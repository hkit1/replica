package com.example.hkit.repository;

import com.example.hkit.entity.PostRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRelationshipRepository extends JpaRepository<PostRelationship, Long> {
    void deleteByOriginalIdAndReplyId(Long originalId, Long replyId);
}
