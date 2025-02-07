package com.son.board.oauth2.service;

import com.son.board.oauth2.domain.CustomOAuth2User;
import com.son.board.oauth2.dto.OAuth2UserInfo;
import com.son.board.oauth2.util.OAuth2UserInfoExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        // 현재 로그인 시도한 OAuth2 공급자 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo userInfo = OAuth2UserInfoExtractor.extract(oauth2User, registrationId);
        String oauthId = userInfo.getOauthId();
        String snsName = userInfo.getSnsName();
        log.info("oauthId: {}, snsName: {}", oauthId, snsName);

        return new CustomOAuth2User(oauth2User, oauthId, snsName);
    }
}