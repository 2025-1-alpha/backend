package com.geulowup.backend.dto;

import com.geulowup.backend.domain.user.entity.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserSummary(
        Long userId,
        String name,
        String email,
        String profileImageUrl,
        LocalDateTime createdAt
) {
    public static UserSummary from(User user) {
        return UserSummary.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
