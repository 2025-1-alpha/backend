package com.geulowup.backend.domain.template.dto.response;


import lombok.Builder;

import java.util.List;

@Builder
public record TemplateAuthorInfoResponse(
        AuthorDetail author,
        int templateTotalCount,
        List<TemplateSummary> templates
) {
}
