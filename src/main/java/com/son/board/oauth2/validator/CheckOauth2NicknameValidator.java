package com.son.board.oauth2.validator;

import com.son.board.dto.UserSignUpRequestDto;
import com.son.board.oauth2.dto.OAuth2SignUpRequestDto;
import com.son.board.repository.UserRepository;
import com.son.board.validator.AbstractValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckOauth2NicknameValidator extends AbstractValidator<OAuth2SignUpRequestDto> {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return OAuth2SignUpRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    protected void doValidate(OAuth2SignUpRequestDto dto, Errors errors) {
        if(userRepository.existsByNickname(dto.getNickname())) {
            errors.rejectValue("nickname", "닉네임 중복", "이미 사용 중인 닉네임입니다.");
        }
    }
}
