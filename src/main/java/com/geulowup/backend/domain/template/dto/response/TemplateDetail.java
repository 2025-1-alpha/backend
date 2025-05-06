package com.geulowup.backend.domain.template.dto.response;

import com.geulowup.backend.domain.template.entity.Template;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;

@Builder
public record TemplateDetail(
        Long templateId,
        AuthorDetail author,
        boolean isAuthor,
        String title,
        String content,
        List<String> tags,
        int likeCount,
        boolean isPrivate
) {
    public static TemplateDetail from(Template template, Long userId) {
        return TemplateDetail.builder()
                .templateId(template.getId())
                .author(AuthorDetail.from(template.getAuthor()))
                .title(template.getTitle())
                .content(template.getContent())
                .isAuthor(template.isAuthor(userId))
                .tags(Arrays.asList(template.getTags().split(",")))
                .likeCount(template.getLikeCount())
                .isPrivate(template.isPrivate())
                .build();
    }
}
