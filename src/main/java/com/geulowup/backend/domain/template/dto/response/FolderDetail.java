package com.geulowup.backend.domain.template.dto.response;

import com.geulowup.backend.domain.template.entity.Template;
import com.geulowup.backend.domain.template.entity.UserTemplateFolder;
import java.util.List;
import lombok.Builder;

@Builder
public record FolderDetail(
        FolderSummary folder,
        List<TemplateSummary> templates
) {
    public static FolderDetail from(UserTemplateFolder folder, List<Template> templates) {
        return FolderDetail.builder()
                .folder(FolderSummary.from(folder))
                .templates(templates.stream().map(TemplateSummary::from).toList())
                .build();
    }
}
