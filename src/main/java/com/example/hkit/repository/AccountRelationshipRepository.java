package com.example.hkit.repository;

import com.example.hkit.entity.AccountRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRelationshipRepository extends JpaRepository<AccountRelationship, Long> {
    void deleteByFollowerIdAndFollowedId(Long followerId, Long followedId);
}
