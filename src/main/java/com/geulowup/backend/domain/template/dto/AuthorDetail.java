package com.geulowup.backend.domain.template.dto;

import com.geulowup.backend.domain.user.entity.User;
import lombok.Builder;

@Builder
public record AuthorDetail(
        Long authorId,
        String authorName,
        int authorScore,
        String authorProfileImageUrl
) {
    public static AuthorDetail from (User user) {
        return AuthorDetail.builder()
                .authorId(user.getId())
                .authorName(user.getName())
                .authorScore(user.getScore())
                .authorProfileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
