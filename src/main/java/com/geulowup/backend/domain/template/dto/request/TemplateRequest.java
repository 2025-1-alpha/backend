package com.geulowup.backend.domain.template.dto.request;

import java.util.List;

public record TemplateRequest(
        String title,
        String content,
        int likeCount,
        List<String> tags,
        boolean isPrivate
) {

}
