package com.son.board.oauth2.dto;

public class OAuth2UserInfo {
    private final String oauthId;
    private final String snsName;

    public OAuth2UserInfo(String oauthId, String snsName) {
        this.oauthId = oauthId;
        this.snsName = snsName;
    }

    public String getOauthId() {
        return oauthId;
    }

    public String getSnsName() {
        return snsName;
    }
}
