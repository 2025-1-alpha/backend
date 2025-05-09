package com.geulowup.backend.domain.template.dto.response;

import com.geulowup.backend.domain.template.entity.Template;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;

@Builder
public record TemplateSummary(
        @Schema(description = "템플릿 ID", example = "1001")
        Long templateId,

        @Schema(description = "템플릿 제목", example = "합격하는 자기소개 예시")
        String title,

        @Schema(description = "템플릿 내용", example = "저는 어떤 상황에서도 책임감을 가지고...")
        String content,

        @ArraySchema(
                schema = @Schema(
                        description = "관심 태그 (지정된 값 중 선택)",
                        allowableValues = {
                                "인사말", "자기소개", "사과문", "부탁글", "감사글", "제안글", "공지글", "소개글",
                                "후기작성", "소셜글", "고객응대", "제휴제안", "교수문의", "조별활동", "공모전", "지원서"
                        },
                        example = "자기소개"
                )
        )
        List<String> tags,

        @Schema(description = "추천 수", example = "42")
        int likeCount
) {
    public static TemplateSummary from(Template template) {
        return TemplateSummary.builder()
                .templateId(template.getId())
                .title(template.getTitle())
                .content(template.getContent())
                .tags(Arrays.asList(template.getTags().split(",")))
                .likeCount(template.getLikeCount())
                .build();
    }
}
