package com.son.board.dto;

import com.son.board.validator.ValidationGroups;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String username;

    @Size(min = 2, max = 10, message = "닉네임은 2~10자 사이여야 합니다.", groups = ValidationGroups.SizeGroup.class)
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]+$", message = "닉네임은 특수문자, 공백을 포함할 수 없습니다.", groups = ValidationGroups.PatternGroup.class)
    private String nickname;
}
