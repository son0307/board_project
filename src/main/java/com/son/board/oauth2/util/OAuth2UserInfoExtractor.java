package com.son.board.oauth2.util;

import com.son.board.oauth2.dto.OAuth2UserInfo;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class OAuth2UserInfoExtractor {
    public static OAuth2UserInfo extract(OAuth2User oAuth2User, String registrationId) {
        String oauthId = "";
        String snsName = "";

        // 공급자별로 응답 데이터 처리
        if (registrationId.equalsIgnoreCase("google")) {
            // Google의 경우 고유 ID는 "sub" 필드에 있음
            oauthId = oAuth2User.getAttribute("sub");
            snsName = oAuth2User.getAttribute("name");
        }
        else if (registrationId.equalsIgnoreCase("naver")) {
            // Naver의 경우 사용자 정보가 response 안에 있음
            @SuppressWarnings("unchecked")
            Map<String, Object> responseMap = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            oauthId = (String) responseMap.get("id");
            snsName = (String) responseMap.get("nickname");
        }

        return new OAuth2UserInfo(oauthId, snsName);
    }
}
