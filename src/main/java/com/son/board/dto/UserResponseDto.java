package com.son.board.dto;

import com.son.board.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

/* 사용자 조회 처리용 dto */
@Getter
public class UserResponseDto {
    private final int id;
    private final String username;
    private final String nickname;
    private final LocalDateTime register_date;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.register_date = user.getRegister_date();
    }
}
