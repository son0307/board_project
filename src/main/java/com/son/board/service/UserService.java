package com.son.board.service;

import com.son.board.domain.User;
import com.son.board.dto.UserSignUpRequestDto;
import com.son.board.dto.UserResponseDto;
import com.son.board.dto.UserUpdateRequestDto;
import com.son.board.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SecurityContextService securityContextService;

    private final EntityManager entityManger;
    private final BCryptPasswordEncoder encoder;

    /* 유저 등록 */
    @Transactional
    public void saveUser(UserSignUpRequestDto request) {
        request.setPassword(encoder.encode(request.getPassword()));
        User newUser = request.toUserEntity();
        userRepository.save(newUser);
        log.info("nickname : {} 등록", request.getNickname());
    }

    /* 유저 조회 */
    @Transactional(readOnly = true)
    public UserResponseDto findUser(int userId) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(userId + " : 사용자 정보가 없습니다."));

        log.info("userId : {} 조회", userId);
        return new UserResponseDto(targetUser);
    }

    /* 유저 정보 갱신 */
    @Transactional
    public void updateUser(int userId, UserUpdateRequestDto request) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(userId + " : 사용자 정보가 없습니다."));

        targetUser.update(request.getNickname());
        userRepository.save(targetUser);

        securityContextService.refreshUserDetails(request.getUsername());

        log.info("userId : {} 갱신", userId);
    }

    /* 유저 삭제 */
    @Transactional
    public void deleteUser(int userId) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(userId + " : 사용자 정보가 없습니다."));

        userRepository.deleteById(targetUser.getId());

        entityManger.flush();
        entityManger.clear();

        log.info("userId : {} 삭제", userId);
    }

    /* 아이디, 닉네임 중복 여부 검사 */
    @Transactional(readOnly = true)
    public boolean checkUsernameDuplication(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean checkNicknameDuplication(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
