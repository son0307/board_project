package com.son.board.validator;

import com.son.board.dto.UserUpdateRequestDto;
import com.son.board.repository.UserRepository;
import com.son.board.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class UpdateUsernameValidator extends AbstractValidator<UserUpdateRequestDto>{

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdateRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    protected void doValidate(UserUpdateRequestDto dto, Errors errors) {
        String username = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();

        userRepository.findByUsername(dto.getUsername()).ifPresent(existingUser -> {
            if(!existingUser.getUsername().equals(username)) {
                errors.rejectValue("username", "아이디 중복", "이미 사용 중인 아이디입니다.");
            }
        });
    }
}
