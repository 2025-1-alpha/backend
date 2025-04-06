package com.geulowup.backend.domain.user.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SocialType {
    GOOGLE("google"), KAKAO("kakao"), UNKNOWN("unknown");

    private final String registrationId;

    public static SocialType findByRegistrationId(String registrationId) {
        if (registrationId.equals("kakao")) {
            return KAKAO;
        } else if (registrationId.equals("google")) {
            return GOOGLE;
        }

        return UNKNOWN;
    }
}
