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
public class UpdateNicknameValidator extends AbstractValidator<UserUpdateRequestDto>{

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdateRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    protected void doValidate(UserUpdateRequestDto dto, Errors errors) {
        String nickname = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getNickname();

        userRepository.findByNickname(dto.getNickname()).ifPresent(existingUser -> {
            if(!existingUser.getNickname().equals(nickname)) {
                errors.rejectValue("nickname", "닉네임 중복", "이미 사용 중인 닉네임입니다.");
            }
        });
    }
}
