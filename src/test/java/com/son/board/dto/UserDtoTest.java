package com.son.board.dto;

import com.son.board.validator.ValidationGroups.*;
import com.son.board.validator.ValidationSequences;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class UserDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("userRequestDto username, password, nickname에 null이 담기면 안된다.")
    public void userRequestDto_nullTest() {
        UserRequestDto requestDto = UserRequestDto.builder().username(null).password(null).nickname(null).build();
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(requestDto, ValidationSequences.class);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("userRequestDto username, password, nickname이 비어있으면 안된다.")
    public void userRequestDto_emptyTest() {
        UserRequestDto requestDto = UserRequestDto.builder().username("").password("").nickname("").build();
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(requestDto, ValidationSequences.class);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("userRequestDto username, password, nickname이 공백이면 안된다.")
    public void userRequestDto_blankTest() {
        UserRequestDto requestDto = UserRequestDto.builder().username(" ").password(" ").nickname(" ").build();
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(requestDto, ValidationSequences.class);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("userRequestDto nickname은 2~10자 사이, password는 8자 이상이어야 한다.")
    public void userRequestDto_nicknameSizeTest() {
        UserRequestDto requestDto = UserRequestDto.builder().nickname("nickname1234").password("1234").build();
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(requestDto, SizeGroup.class);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("userRequestDto nickname은 공백 혹은 특수문자를 사용할 수 없다.")
    public void userRequestDto_nicknameTest() {
        UserRequestDto requestDto1 = UserRequestDto.builder().nickname("닉 네 임").build();
        Set<ConstraintViolation<UserRequestDto>> violations1 = validator.validate(requestDto1, PatternGroup.class);

        violations1.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations1.size()).isEqualTo(1);

        UserRequestDto requestDto2 = UserRequestDto.builder().nickname("닉@네@임").build();
        Set<ConstraintViolation<UserRequestDto>> violations2 = validator.validate(requestDto2, PatternGroup.class);

        violations2.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations2.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("userRequestDto password는 영문, 숫자, 특수문자를 포함해야 한다.")
    public void userRequestDto_passwordTest() {
        UserRequestDto requestDto = UserRequestDto.builder().password("abcd1234!@#$").build();
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(requestDto, PatternGroup.class);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("userRequestDto 검증 통과 테스트")
    public void userRequestDto_successTest() {
        UserRequestDto requestDto = UserRequestDto.builder().username("test").password("test1234!@#$").nickname("test").build();
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(requestDto, PatternGroup.class);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations.size()).isEqualTo(0);
    }
}
