package com.geulowup.backend.domain.template.dto.response;

import com.geulowup.backend.domain.template.entity.UserTemplateFolder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record FolderSummary(

        @Schema(description = "폴더 ID", example = "1")
        Long folderId,

        @Schema(description = "폴더 이름", example = "자주 쓰는 템플릿")
        String name
) {
    public static FolderSummary from(UserTemplateFolder folder) {
        return FolderSummary.builder()
                .folderId(folder.getId())
                .name(folder.getName())
                .build();
    }
}
