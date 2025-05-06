package com.geulowup.backend.domain.template.dto.response;

import com.geulowup.backend.domain.template.entity.Template;
import lombok.Builder;

@Builder
public record TemplateSummary(
        Long templateId,
        String title,
        String content,
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
