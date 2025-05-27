package com.geulowup.backend.domain.user.controller;

import com.geulowup.backend.domain.user.dto.request.SignupRequest;
import com.geulowup.backend.domain.user.dto.response.CurrentUserInfoResponse;
import com.geulowup.backend.domain.user.dto.request.UserNicknameUpdateRequest;
import com.geulowup.backend.domain.user.service.UserService;
import com.geulowup.backend.global.security.oauth2.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@AuthenticationPrincipal CustomOAuth2User principal, @RequestBody SignupRequest request) {
        userService.signUp(principal.getUserId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUserInfoResponse> getCurrentUser(@AuthenticationPrincipal CustomOAuth2User principal) {
        return ResponseEntity.ok(userService.getCurrentUser(principal.getUserId()));
    }

    @PatchMapping("/me/nickname")
    public ResponseEntity<Void> updateNickname(@AuthenticationPrincipal CustomOAuth2User principal, @RequestBody UserNicknameUpdateRequest request) {
        userService.updateNickname(principal.getUserId(), request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/me/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateProfileImage(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @RequestPart(required = false) MultipartFile image
    ) {
        userService.updateProfileImage(principal.getUserId(), image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal CustomOAuth2User principal) {
        userService.deleteUser(principal.getUserId());

        return ResponseEntity.ok().build();
    }

}

