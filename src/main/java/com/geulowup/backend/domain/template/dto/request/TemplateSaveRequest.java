package com.geulowup.backend.domain.template.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record TemplateSaveRequest(
        @Schema(description = "폴더 ID", example = "12")
        Long folderId,

        @Schema(description = "사용자가 수정한 템플릿 내용 (수정하면)", example = "안녕하세요, ...")
        String content
) {
}
