package com.geulowup.backend.global.security.oauth2;

import com.geulowup.backend.domain.user.enums.SocialType;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        System.out.println(user.getAttributes());
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = SocialType.findByRegistrationId(registrationId);
        return OAuth2Provider.getCustomOAuth2User(user, socialType);
    }
}