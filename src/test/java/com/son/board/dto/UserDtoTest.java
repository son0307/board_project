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
        UserSignUpRequestDto requestDto = UserSignUpRequestDto.builder().username(null).password(null).nickname(null).build();
        Set<ConstraintViolation<UserSignUpRequestDto>> violations = validator.validate(requestDto, ValidationSequences.class);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("userRequestDto username, password, nickname이 비어있으면 안된다.")
    public void userRequestDto_emptyTest() {
        UserSignUpRequestDto requestDto = UserSignUpRequestDto.builder().username("").password("").nickname("").build();
        Set<ConstraintViolation<UserSignUpRequestDto>> violations = validator.validate(requestDto, ValidationSequences.class);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("userRequestDto username, password, nickname이 공백이면 안된다.")
    public void userRequestDto_blankTest() {
        UserSignUpRequestDto requestDto = UserSignUpRequestDto.builder().username(" ").password(" ").nickname(" ").build();
        Set<ConstraintViolation<UserSignUpRequestDto>> violations = validator.validate(requestDto, ValidationSequences.class);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("userRequestDto nickname은 2~10자 사이, password는 8자 이상이어야 한다.")
    public void userRequestDto_nicknameSizeTest() {
        UserSignUpRequestDto requestDto = UserSignUpRequestDto.builder().nickname("nickname1234").password("1234").build();
        Set<ConstraintViolation<UserSignUpRequestDto>> violations = validator.validate(requestDto, SizeGroup.class);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("userRequestDto nickname은 공백 혹은 특수문자를 사용할 수 없다.")
    public void userRequestDto_nicknameTest() {
        UserSignUpRequestDto requestDto1 = UserSignUpRequestDto.builder().nickname("닉 네 임").build();
        Set<ConstraintViolation<UserSignUpRequestDto>> violations1 = validator.validate(requestDto1, PatternGroup.class);

        violations1.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations1.size()).isEqualTo(1);

        UserSignUpRequestDto requestDto2 = UserSignUpRequestDto.builder().nickname("닉@네@임").build();
        Set<ConstraintViolation<UserSignUpRequestDto>> violations2 = validator.validate(requestDto2, PatternGroup.class);

        violations2.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations2.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("userRequestDto password는 영문, 숫자, 특수문자를 포함해야 한다.")
    public void userRequestDto_passwordTest() {
        UserSignUpRequestDto requestDto = UserSignUpRequestDto.builder().password("abcd1234!@#$").build();
        Set<ConstraintViolation<UserSignUpRequestDto>> violations = validator.validate(requestDto, PatternGroup.class);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("userRequestDto 검증 통과 테스트")
    public void userRequestDto_successTest() {
        UserSignUpRequestDto requestDto = UserSignUpRequestDto.builder().username("test").password("test1234!@#$").nickname("test").build();
        Set<ConstraintViolation<UserSignUpRequestDto>> violations = validator.validate(requestDto, PatternGroup.class);

        violations.forEach(i -> System.out.println(i.getMessage()));
        Assertions.assertThat(violations.size()).isEqualTo(0);
    }
}
