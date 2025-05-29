package com.geulowup.backend.domain.user.dto.response;

import com.geulowup.backend.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CurrentUserInfoResponse(

        @Schema(description = "사용자 ID", example = "1001")
        Long userId,

        @Schema(description = "사용자 이름", example = "수민")
        String name,

        @Schema(description = "사용자 이메일", example = "suminglowup@example.com")
        String email,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/images/profile.jpg")
        String profileImageUrl,

        @Schema(description = "글로우 온도", example = "10.5")
        Double score,

        @Schema(description = "가입 일시 (ISO 8601 형식)", example = "2025-05-07T14:23:45")
        LocalDateTime createdAt
) {

    public static CurrentUserInfoResponse from(User user) {
        return CurrentUserInfoResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .score(user.getScore())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
