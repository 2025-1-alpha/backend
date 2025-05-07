package com.geulowup.backend.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserNicknameUpdateRequest(
        @Schema(description = "변경할 닉네임", example = "황수민")
        String nickname

) {
}
