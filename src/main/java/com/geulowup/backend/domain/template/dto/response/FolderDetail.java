package com.geulowup.backend.domain.template.dto.response;

import com.geulowup.backend.domain.template.entity.Template;
import com.geulowup.backend.domain.template.entity.UserTemplateFolder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
public record FolderDetail(
        @Schema(description = "폴더 정보 요약")
        FolderSummary folder,

        @Schema(description = "폴더에 포함된 템플릿 목록")
        List<TemplateSummary> templates
) {
    public static FolderDetail from(UserTemplateFolder folder, List<Template> templates) {
        return FolderDetail.builder()
                .folder(FolderSummary.from(folder))
                .templates(templates.stream().map(TemplateSummary::from).toList())
                .build();
    }
}
