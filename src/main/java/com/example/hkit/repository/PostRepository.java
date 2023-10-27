package com.example.hkit.repository;

import com.example.hkit.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Set<Post> findAllByContentContains(String text);

    Optional<Post> findPostByAuthor_AccountIDAndContent(String accountId, String content);

    @Query("SELECT e FROM Post e WHERE e.id > :id ORDER BY e.id ASC")
    List<Post> findTop5ItemsAfterId(@Param("id") Long id, Pageable pageable);
}
