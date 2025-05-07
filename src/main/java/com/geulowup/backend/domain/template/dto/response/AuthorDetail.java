package com.geulowup.backend.domain.template.dto.response;

import com.geulowup.backend.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AuthorDetail(
        @Schema(description = "작성자 ID", example = "42")
        Long id,

        @Schema(description = "작성자 이름", example = "홍길동")
        String name,

        @Schema(description = "작성자 점수", example = "85")
        int score,

        @Schema(description = "작성자 프로필 이미지 URL", example = "https://example.com/images/profile.jpg")
        String profileImageUrl
) {
    public static AuthorDetail from (User user) {
        return AuthorDetail.builder()
                .id(user.getId())
                .name(user.getName())
                .score(user.getScore())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
