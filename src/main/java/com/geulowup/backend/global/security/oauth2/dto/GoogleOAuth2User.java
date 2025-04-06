package com.geulowup.backend.global.security.oauth2.dto;

import com.geulowup.backend.domain.user.enums.SocialType;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleOAuth2User extends CustomOAuth2User {
    public GoogleOAuth2User(OAuth2User user,
                           SocialType socialType) {
        super(user, socialType);
    }

    @Override
    public String getEmail() {
        return (String) super.getAttributes().get("email");
    }

    @Override
    public String getSocialName() {
        return (String) super.getAttributes().get("name");
    }

    @Override
    public String getSocialId() {
        return (String) super.getAttributes().get("sub");
    }
}
