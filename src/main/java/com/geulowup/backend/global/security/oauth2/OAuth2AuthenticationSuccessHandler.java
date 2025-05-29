package com.geulowup.backend.global.security.oauth2;

import com.geulowup.backend.domain.user.entity.User;
import com.geulowup.backend.domain.user.enums.SocialType;
import com.geulowup.backend.domain.user.repository.UserRepository;
import com.geulowup.backend.global.jwt.TokenProvider;
import com.geulowup.backend.global.security.oauth2.dto.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;

    @Value("${login.redirect.uri}")
    private String loginRedirectUri;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String socialId = oAuth2User.getSocialId();
        SocialType socialType = oAuth2User.getSocialType();
        User user = userRepository.findBySocialIdAndSocialType(socialId, socialType)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .socialId(socialId)
                            .email(oAuth2User.getEmail())
                            .socialType(socialType)
                            .profileImageUrl("https://github.com/user-attachments/assets/9c948b08-a78b-44cb-b572-f2a934b70c45")
                            .score(0.0)
                            .build();
                    return userRepository.save(newUser);
                });

        String accessToken = tokenProvider.generateToken(user, Duration.ofDays(30));
        CallbackType type = (user.getName() == null) ? CallbackType.NEW_USER : CallbackType.SUCCESS;

        getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(loginRedirectUri + "/callback", type, accessToken));
    }

    private String getRedirectUrl(String targetUrl, CallbackType type, String token) {
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("type", type)
                .queryParam("token", token)
                .build().toUriString();
    }

    enum CallbackType {
        SUCCESS, NEW_USER
    }
}