package com.son.board.repository;

import com.son.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    @Modifying
    @Query("UPDATE Post p set p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    int updateView(int id);
}
