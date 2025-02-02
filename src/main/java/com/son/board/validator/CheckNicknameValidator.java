package com.son.board.validator;

import com.son.board.dto.UserSignUpRequestDto;
import com.son.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckNicknameValidator extends AbstractValidator<UserSignUpRequestDto>{

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserSignUpRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    protected void doValidate(UserSignUpRequestDto dto, Errors errors) {
        if(userRepository.existsByNickname(dto.getNickname())) {
            errors.rejectValue("nickname", "닉네임 중복", "이미 사용 중인 닉네임입니다.");
        }
    }
}
