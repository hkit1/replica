package com.example.hkit.repository;

import com.example.hkit.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByContentContains(String text);

    @Query("SELECT e FROM Post e WHERE e.id > :id ORDER BY e.id ASC")
    List<Post> findTop5ItemsAfterId(@Param("id") Long id, Pageable pageable);
}
