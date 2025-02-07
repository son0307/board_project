package com.son.board.oauth2.service;

import com.son.board.domain.User;
import com.son.board.oauth2.domain.CustomOAuth2User;
import com.son.board.oauth2.dto.OAuth2RegistrationDto;
import com.son.board.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        // Google의 경우 고유 ID는 "sub" 필드에 있음
        String oauthId = oauth2User.getAttribute("sub");
        String snsName = oauth2User.getAttribute("name");

        Optional<User> userOptional = userRepository.findByUsername(oauthId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new CustomOAuth2User(oauth2User, user.getUsername(), user.getNickname());
        } else {
            // 회원가입 미완료: 회원가입 페이지로 넘길 정보를 세션에 저장
            OAuth2RegistrationDto registrationDto = OAuth2RegistrationDto.builder()
                    .username(oauthId)
                    .nickname(snsName)
                    .build();
            httpSession.setAttribute("oauth2Registration", registrationDto);
            return new CustomOAuth2User(oauth2User, oauthId, snsName);
        }
    }
}

