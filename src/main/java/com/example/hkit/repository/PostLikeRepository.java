package com.example.hkit.repository;

import com.example.hkit.entity.Post;
import com.example.hkit.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Set<PostLike> findAllByPostId(Long id);

    long countPostLikeByPostId(Long id);
}
