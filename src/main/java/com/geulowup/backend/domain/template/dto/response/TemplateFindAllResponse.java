package com.geulowup.backend.domain.template.dto.response;


import lombok.Builder;

import java.util.List;

@Builder
public record TemplateFindAllResponse(
        List<TemplateSummary> templates,
        long totalPage
) {
}
