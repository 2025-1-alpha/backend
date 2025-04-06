package com.geulowup.backend.global.security.oauth2.dto;

import com.geulowup.backend.domain.user.enums.SocialType;
import java.util.HashMap;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class KakaoOAuth2User extends CustomOAuth2User {
    public KakaoOAuth2User(OAuth2User user,
                           SocialType socialType) {
        super(user, socialType);
    }

    @Override
    public String getEmail() {
        HashMap<String, String> map = (HashMap<String, String>) super.getAttributes().get("kakao_account");
        if (map == null)
            return "";
        return map.get("email");
    }

    @Override
    public String getSocialName() {
        HashMap<String, String> map = (HashMap<String, String>) super.getAttributes().get("properties");
        if (map == null) return "";
        return map.get("nickname");
    }

    @Override
    public String getSocialId() {
        return super.getAttributes().get("id").toString();
    }
}
