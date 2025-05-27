package com.geulowup.backend.domain.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ModelSpellCheckRequest(
        @Schema(description = "맞춤법 검사 요청할 내용 (본문)", example = "본문입니다. 블라블라~")
        String content
) {
}
