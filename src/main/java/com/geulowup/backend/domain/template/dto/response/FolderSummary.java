package com.geulowup.backend.domain.template.dto.response;

import com.geulowup.backend.domain.template.entity.UserTemplateFolder;
import lombok.Builder;

@Builder
public record FolderSummary(
        Long folderId,
        String name
) {
    public static FolderSummary from(UserTemplateFolder folder) {
        return FolderSummary.builder()
                .folderId(folder.getId())
                .name(folder.getName())
                .build();
    }
}
