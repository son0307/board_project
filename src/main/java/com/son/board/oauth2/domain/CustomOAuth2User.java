package com.son.board.oauth2.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User oauth2User;
    private final String id;
    private final String nickname;

    public CustomOAuth2User(OAuth2User oauth2User, String id, String nickname) {
        this.oauth2User = oauth2User;
        this.id = id;
        this.nickname = nickname;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getName();
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}

