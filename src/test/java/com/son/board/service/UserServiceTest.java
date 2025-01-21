package com.son.board.service;

import com.son.board.domain.User;
import com.son.board.dto.UserRequestDto;
import com.son.board.dto.UserResponseDto;
import com.son.board.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void reset() {
        entityManager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 1").executeUpdate();
    }

    /* 사용자 등록 테스트 */
    @Test
    public void saveTest() {
        // given
        UserRequestDto user = UserRequestDto.builder()
                .username("ID")
                .nickname("닉네임")
                .password("1234")
                .register_date(LocalDateTime.now())
                .build();

        // when
        userService.saveUser(user);

        // then
        Optional<User> savedUser = userRepository.findById(1);

        Assertions.assertThat(savedUser.get().getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(savedUser.get().getNickname()).isEqualTo(user.getNickname());
    }

    /* 사용자 조회 테스트 */
    @Test
    public void loadTest() {
        // given
        UserRequestDto user = UserRequestDto.builder()
                .username("ID")
                .nickname("닉네임")
                .password("1234")
                .register_date(LocalDateTime.now())
                .build();

        userService.saveUser(user);

        // when
        UserResponseDto findUser = userService.findUser(1);

        // then
        Assertions.assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(findUser.getNickname()).isEqualTo(user.getNickname());
    }

    /* 사용자 수정 테스트 */
    @Test
    public void updateTest() {
        // given
        UserRequestDto user = UserRequestDto.builder()
                .username("ID")
                .nickname("닉네임")
                .password("1234")
                .register_date(LocalDateTime.now())
                .build();

        userService.saveUser(user);

        UserRequestDto updatedUser = UserRequestDto.builder()
                .username("ID")
                .nickname("새로운 닉네임")
                .password("abcd")
                .register_date(user.getRegister_date())
                .build();

        // when
        userService.updateUser(1, updatedUser);

        // then
        UserResponseDto targetUser = userService.findUser(1);

        Assertions.assertThat(targetUser.getUsername()).isEqualTo(updatedUser.getUsername());
        Assertions.assertThat(targetUser.getNickname()).isEqualTo(updatedUser.getNickname());
    }
}
