package com.geulowup.backend.domain.template.dto;

import com.geulowup.backend.domain.template.entity.Template;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;

@Builder
public record TemplateDetail(
        Long templateId,
        AuthorDetail author,
        String title,
        String content,
        List<String> keywords,
        int likeCount,
        boolean isPrivate
) {
    public static TemplateDetail from(Template template) {
        return TemplateDetail.builder()
                .templateId(template.getId())
                .author(AuthorDetail.from(template.getAuthor()))
                .title(template.getTitle())
                .content(template.getContent())
                .keywords(Arrays.asList(template.getKeywords().split(",")))
                .likeCount(template.getLikeCount())
                .isPrivate(template.isPrivate())
                .build();
    }
}
