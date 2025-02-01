package com.son.board.repository;

import com.son.board.domain.Post;
import com.son.board.domain.PostLike;
import com.son.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    Optional<PostLike> findByPostAndUser(Post post, User user);
    boolean existsByPostAndUser(Post post, User user);
    int countByPost(Post post);
}
