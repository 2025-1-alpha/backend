package com.geulowup.backend.domain.template.dto.response;

import com.geulowup.backend.domain.template.entity.Template;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record TemplateSummary(
        @Schema(description = "템플릿 ID", example = "1001")
        Long templateId,

        @Schema(description = "템플릿 제목", example = "합격하는 자기소개 예시")
        String title,

        @Schema(description = "템플릿 내용", example = "저는 어떤 상황에서도 책임감을 가지고...")
        String content,

        @Schema(description = "추천 수", example = "42")
        int likeCount
) {
    public static TemplateSummary from(Template template) {
        return TemplateSummary.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .content(template.getContent())
                .likeCount(template.getLikeCount())
                .build();
    }
}
