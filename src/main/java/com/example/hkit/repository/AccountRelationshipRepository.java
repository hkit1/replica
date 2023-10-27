package com.example.hkit.repository;

import com.example.hkit.entity.Account;
import com.example.hkit.entity.AccountRelationship;
import com.example.hkit.entity.PostRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
public interface AccountRelationshipRepository extends JpaRepository<AccountRelationship, Long> {
    void deleteByFollowerIdAndFollowedId(Long followerId, Long followedId);

    List<AccountRelationship> findAccountRelationshipsByFollowed_Id(Long followerId);



}
