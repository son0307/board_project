package com.son.board.validator;

import com.son.board.dto.UserRequestDto;
import com.son.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckUsernameValidator extends AbstractValidator<UserRequestDto>{

    private final UserRepository userRepository;

    @Override
    protected void doValidate(UserRequestDto dto, Errors errors) {
        if(userRepository.existsByUsername(dto.getUsername())) {
            errors.rejectValue("username", "아이디 중복", "이미 사용 중인 아이디입니다.");
        }
    }
}
