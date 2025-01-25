package com.son.board.dto;

import com.son.board.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

/* 사용자 등록, 수정 처리용 dto */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String username;
    private String nickname;
    private String password;
    private LocalDateTime register_date;

    public User toUserEntity() {
        if(Objects.isNull(register_date))
            this.register_date = LocalDateTime.now();

        User newUser = User.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .register_date(register_date)
                .build();

        return newUser;
    }
}
