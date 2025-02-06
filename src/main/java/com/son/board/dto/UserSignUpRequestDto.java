package com.son.board.dto;

import com.son.board.domain.User;
import com.son.board.validator.ValidationGroups.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

/* 사용자 등록 처리용 dto */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDto {

    @NotBlank(message = "ID는 비워둘 수 없습니다.", groups = NotBlankGroup.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "ID는 영어 또는 숫자만 사용할 수 있습니다.", groups = PatternGroup.class)
    private String username;

    @NotBlank(message = "닉네임은 비워둘 수 없습니다.", groups = NotBlankGroup.class)
    @Size(min = 2, max = 10, message = "닉네임은 2~10자 사이여야 합니다.", groups = SizeGroup.class)
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]+$", message = "닉네임은 특수문자, 공백을 포함할 수 없습니다.", groups = PatternGroup.class)
    private String nickname;

    @NotBlank(message = "비밀번호는 비워둘 수 없습니다.", groups = NotBlankGroup.class)
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.", groups = SizeGroup.class)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])[^\\s]+$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 하며 공백을 포함할 수 없습니다.", groups = PatternGroup.class)
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
