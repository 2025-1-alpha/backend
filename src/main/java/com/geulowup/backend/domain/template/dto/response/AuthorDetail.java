package com.geulowup.backend.domain.template.dto.response;

import com.geulowup.backend.domain.user.entity.User;
import lombok.Builder;

@Builder
public record AuthorDetail(
        Long id,
        String name,
        int score,
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
