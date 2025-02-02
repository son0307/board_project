package com.son.board.controller;

import com.son.board.dto.UserSignUpRequestDto;
import com.son.board.dto.UserResponseDto;
import com.son.board.dto.UserUpdateRequestDto;
import com.son.board.service.UserService;
import com.son.board.validator.*;
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

    /* 회원가입 검증기 */
    private final CheckUsernameValidator checkUsernameValidator;
    private final CheckNicknameValidator checkNicknameValidator;

    /* 회원정보 수정 검증기 */
    private final UpdateUsernameValidator updateUsernameValidator;
    private final UpdateNicknameValidator updateNicknameValidator;

    /* 회원가입 Validator 연결 */
    @InitBinder("signUpInput")
    public void initSignUpBinder(WebDataBinder binder) {
        binder.addValidators(checkUsernameValidator, checkNicknameValidator);
    }

    /* 회원정보 수정 Validator 연결 */
    @InitBinder("updateInput")
    public void initUpdateBinder(WebDataBinder binder) {
        binder.addValidators(updateUsernameValidator, updateNicknameValidator);
    }

    /* 회원가입 페이지로 이동 */
    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("signUpInput", new UserSignUpRequestDto());

        return "user/signup";
    }

    /* 회원가입 프로세스 */
    @PostMapping("/signup")
    public String signUpProc(@Validated(ValidationSequences.class)
                                        @ModelAttribute("signUpInput") UserSignUpRequestDto request,
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

    /* 유저 정보 페이지로 이동 */
    @GetMapping("/user/{userId}")
    public String userDetail(@PathVariable int userId, Model model) {
        UserResponseDto responseDto = userService.findUser(userId);
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto();
        userUpdateRequestDto.setNickname(responseDto.getNickname());

        model.addAttribute("userInfo", responseDto);
        model.addAttribute("updateInput", userUpdateRequestDto);

        return "/user/detail";
    }

    /* 유저 정보 수정 프로세스 */
    @PostMapping("/user/{userId}")
    public String userDetailProc(@PathVariable int userId,
                                 @Validated(ValidationSequences.class)
                                 @ModelAttribute("updateInput") UserUpdateRequestDto request,
                                 BindingResult bindingResult,
                                 Model model) {
        UserResponseDto userInfo = userService.findUser(userId);
        model.addAttribute("userInfo", userInfo);

        if(bindingResult.hasErrors()) {
            model.addAttribute("updateInput", request);
            log.info(bindingResult.getAllErrors().toString());
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);

            return "/user/detail";
        }

        userService.updateUser(userId, request);

        return "redirect:/user/" + userId;
    }
}
