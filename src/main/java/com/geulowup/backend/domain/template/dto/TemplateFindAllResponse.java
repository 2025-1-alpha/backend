package com.geulowup.backend.domain.template.dto;


import lombok.Builder;

import java.util.List;

@Builder
public record TemplateFindAllResponse(
        List<TemplateSummary> templates
) {
}
