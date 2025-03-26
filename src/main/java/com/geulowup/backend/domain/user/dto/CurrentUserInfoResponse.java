package com.geulowup.backend.domain.user.dto;

import com.geulowup.backend.domain.user.entity.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CurrentUserInfoResponse(
        Long userId,
        String name,
        String email,
        String profileImageUrl,
        LocalDateTime createdAt
) {
    public static CurrentUserInfoResponse from(User user) {
        return CurrentUserInfoResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
