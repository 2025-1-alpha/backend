package com.geulowup.backend.domain.template.dto;




import java.util.List;

public record TemplateRequest(
        String title,
        String content,
        int likeCount,
        List<String> keywords,
        boolean isPrivate
) {

}
