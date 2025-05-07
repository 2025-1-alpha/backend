package com.geulowup.backend.domain.template.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record TemplateAuthorInfoResponse(
        @Schema(description = "작성자 정보")
        AuthorDetail author,

        @Schema(description = "작성자의 전체 템플릿 수", example = "12")
        int templateTotalCount,

        @Schema(description = "작성자의 템플릿 목록")
        List<TemplateSummary> templates
) {
}
