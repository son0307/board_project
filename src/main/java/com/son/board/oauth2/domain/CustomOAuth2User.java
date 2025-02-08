package com.son.board.oauth2.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User oauth2User;
    private final String oAuthid;
    private final String snsName;

    public CustomOAuth2User(OAuth2User oauth2User, String oAuthid, String snsName) {
        this.oauth2User = oauth2User;
        this.oAuthid = oAuthid;
        this.snsName = snsName;
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

    public String getoAuthid() {
        return oAuthid;
    }

    public String getSnsName() {
        return snsName;
    }
}

