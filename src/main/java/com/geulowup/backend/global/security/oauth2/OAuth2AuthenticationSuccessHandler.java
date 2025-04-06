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
                            .name(oAuth2User.getName())
                            .email(oAuth2User.getEmail())
                            .socialType(socialType)
                            .score(0)
                            .build();
                    return userRepository.save(newUser);
                });

        String accessToken = tokenProvider.generateToken(user, Duration.ofDays(30));
        getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(loginRedirectUri + "/callback", accessToken));
    }

    private String getRedirectUrl(String targetUrl, String token) {
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }
}