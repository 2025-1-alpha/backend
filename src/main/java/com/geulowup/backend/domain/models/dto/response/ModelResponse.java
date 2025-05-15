package com.geulowup.backend.domain.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ModelResponse(
        @Schema(description = "AI를 거친 결과 글", example = "AI를 거친 결과 글입니다.")
        String result
) {
}
