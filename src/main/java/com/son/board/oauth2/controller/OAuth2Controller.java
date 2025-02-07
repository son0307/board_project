package com.son.board.oauth2.controller;

import com.son.board.dto.UserSignUpRequestDto;
import com.son.board.oauth2.dto.OAuth2SignUpRequestDto;
import com.son.board.oauth2.dto.OAuth2RegistrationDto;
import com.son.board.service.UserService;
import com.son.board.oauth2.validator.CheckOauth2NicknameValidator;
import com.son.board.validator.ValidationSequences;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OAuth2Controller {

    private final UserService userService;

    private final CheckOauth2NicknameValidator nicknameValidator;

    @InitBinder("oAuth2SignUpInput")
    public void initSignUpBinder(WebDataBinder binder) {
        binder.addValidators(nicknameValidator);
    }

    @GetMapping("/oauth2/signup")
    public String signUp(Model model, HttpSession session) {
        // 세션에 저장된 경고 메시지가 있는지 확인
        String registrationWarning = (String) session.getAttribute("registrationWarning");
        if (registrationWarning != null) {
            model.addAttribute("registrationWarning", registrationWarning);
            // 메시지를 모델에 추가한 후 세션에서 제거하여 한 번만 출력되도록 함
            session.removeAttribute("registrationWarning");
        }

        // OAuth2.0 방식으로 회원가입 시, 세션에서 정보를 가져와 DTO 생성
        OAuth2RegistrationDto oAuth2Registration = (OAuth2RegistrationDto) session.getAttribute("oauth2Registration");
        OAuth2SignUpRequestDto requestDto = new OAuth2SignUpRequestDto();

        requestDto.setNickname(oAuth2Registration.getNickname());

        model.addAttribute("oauth2Registration", oAuth2Registration);
        model.addAttribute("oAuth2SignUpInput", requestDto);
        return "oauth2/signup";
    }

    @PostMapping("/oauth2/signup")
    public String signUpProc(@Validated(ValidationSequences.class)
                             @ModelAttribute("oAuth2SignUpInput") OAuth2SignUpRequestDto request,
                             BindingResult bindingResult,
                             Model model,
                             HttpSession session) {
        OAuth2RegistrationDto oAuth2Registration = (OAuth2RegistrationDto) session.getAttribute("oauth2Registration");

        if(bindingResult.hasErrors()) {
            model.addAttribute("oauth2Registration", oAuth2Registration);
            model.addAttribute("oAuth2SignUpInput", request);

            return "oauth2/signup";
        }

        session.removeAttribute("oauth2Registration");

        UserSignUpRequestDto newUser = UserSignUpRequestDto.builder()
                .username(oAuth2Registration.getUsername())
                .password("")
                .nickname(request.getNickname())
                .register_date(null)
                .build();

        userService.saveUser(newUser);

        return "redirect:/";
    }
}
