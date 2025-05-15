package com.geulowup.backend.domain.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ModelPlaceholderRequest(
        @Schema(description = "빈칸 뚫기 요청할 내용 (본문)", example = "본문입니다. 블라블라~")
        String content
) {
}
