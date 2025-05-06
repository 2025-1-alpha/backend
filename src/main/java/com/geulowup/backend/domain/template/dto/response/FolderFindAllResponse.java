package com.geulowup.backend.domain.template.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record FolderFindAllResponse(
        List<FolderSummary> folders
) {
}
