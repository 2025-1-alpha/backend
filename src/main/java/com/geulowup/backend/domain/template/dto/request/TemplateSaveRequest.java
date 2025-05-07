package com.geulowup.backend.domain.template.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record TemplateSaveRequest(
        @Schema(description = "폴더 ID", example = "12")
        Long folderId
) {
}
