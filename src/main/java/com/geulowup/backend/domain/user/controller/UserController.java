package com.geulowup.backend.domain.user.controller;

import com.geulowup.backend.domain.user.dto.CurrentUserInfoResponse;
import com.geulowup.backend.domain.user.service.UserService;
import com.geulowup.backend.global.security.oauth2.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<CurrentUserInfoResponse> getCurrentUser(@AuthenticationPrincipal CustomOAuth2User principal) {
        return ResponseEntity.ok(userService.getCurrentUser(principal.getUserId()));
    }
}

