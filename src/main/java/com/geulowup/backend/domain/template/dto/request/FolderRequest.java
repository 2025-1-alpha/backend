package com.geulowup.backend.domain.template.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record FolderRequest(
        @Schema(description = "폴더 이름", example = "템플릿 폴더 이름")
        String name
) {
}
