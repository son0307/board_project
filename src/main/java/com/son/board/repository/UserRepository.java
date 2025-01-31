package com.son.board.repository;

import com.son.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);

    Optional<User> findByUsername(String username);
}
