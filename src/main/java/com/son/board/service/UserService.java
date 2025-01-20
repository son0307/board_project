package com.son.board.service;

import com.son.board.domain.User;
import com.son.board.dto.UserRequestDto;
import com.son.board.dto.UserResponseDto;
import com.son.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /* 유저 등록 */
    @Transactional
    public void saveUser(UserRequestDto request) {
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
    public void updateUser(int userId, UserRequestDto request) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(userId + " : 사용자 정보가 없습니다."));

        targetUser.update(request.getNickname(), request.getPassword());
        log.info("userId : {} 갱신", userId);
    }

    /* 유저 삭제 */
    @Transactional
    public void deleteUser(int userId) {
        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(userId + " : 사용자 정보가 없습니다."));

        userRepository.deleteById(userId);
        log.info("userId : {} 삭제", userId);
    }
}
