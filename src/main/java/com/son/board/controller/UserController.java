package com.son.board.controller;

import com.son.board.dto.UserRequestDto;
import com.son.board.service.UserService;
import com.son.board.validator.CheckNicknameValidator;
import com.son.board.validator.CheckUsernameValidator;
import com.son.board.validator.ValidationSequences;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* 중복 체크 유효성 검사 */
    private final CheckUsernameValidator checkUsernameValidator;
    private final CheckNicknameValidator checkNicknameValidator;

    /* Validator 연결 */
    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkUsernameValidator);
        binder.addValidators(checkNicknameValidator);
    }

    /* 회원가입 페이지로 이동 */
    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("userInput", new UserRequestDto());

        return "user/signup";
    }

    /* 회원가입 프로세스 */
    @PostMapping("/signup")
    public String signUpProc(@Validated(ValidationSequences.class)
                                        @ModelAttribute("userInput") UserRequestDto request,
                                        BindingResult bindingResult,
                                        Model model) {

        log.info(request.toString());
        if(bindingResult.hasErrors()) {
            model.addAttribute("userInput", request);
            log.info(bindingResult.getAllErrors().toString());
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);

            return "user/signup";
        }

        userService.saveUser(request);

        return "redirect:/";
    }

    /* ID, 닉네임 중복 검사 */
    @GetMapping("/signup/{username}/exists")
    public ResponseEntity<?> checkUsernameDuplication(@PathVariable String username) {
        return ResponseEntity.ok(userService.checkUsernameDuplication(username));
    }

    @GetMapping("/signup/{nickname}/exists")
    public ResponseEntity<?> checkNicknameDuplication(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.checkNicknameDuplication(nickname));
    }

    /* 로그인 페이지로 이동 */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "로그인 실패. 아이디와 비밀번호를 확인해주세요.");
        }

        return "/user/login";
    }
}
