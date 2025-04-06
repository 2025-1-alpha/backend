package com.geulowup.backend.global.security.oauth2;

import com.geulowup.backend.domain.user.enums.SocialType;
import com.geulowup.backend.global.security.oauth2.dto.CustomOAuth2User;
import com.geulowup.backend.global.security.oauth2.dto.GoogleOAuth2User;
import com.geulowup.backend.global.security.oauth2.dto.KakaoOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2Provider {
    public static CustomOAuth2User getCustomOAuth2User(OAuth2User oAuth2User, SocialType socialType) {
        if (socialType == SocialType.KAKAO) {
            return new KakaoOAuth2User(oAuth2User, socialType);
        } else if (socialType == SocialType.GOOGLE) {
            return new GoogleOAuth2User(oAuth2User, socialType);
        }

        return null;
    }
}
