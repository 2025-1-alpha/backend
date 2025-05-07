package com.geulowup.backend.domain.template.dto.response;

import com.geulowup.backend.domain.template.entity.Template;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;

@Builder
public record TemplateDetail(
        @Schema(description = "템플릿 ID", example = "1001")
        Long templateId,

        @Schema(description = "작성자 정보")
        AuthorDetail author,

        @Schema(description = "요청자가 이 템플릿의 작성자인지 여부", example = "true")
        boolean isAuthor,

        @Schema(description = "템플릿 제목", example = "면접에서 어필하기 좋은 자기소개")
        String title,

        @Schema(description = "템플릿 본문", example = "안녕하세요. 저는 책임감 있게 팀 프로젝트를 수행해 온...")
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

        @Schema(description = "추천 수", example = "27")
        int likeCount,

        @Schema(description = "비공개 여부 (true: 비공개, false: 공개)", example = "false")
        boolean isPrivate
) {
    public static TemplateDetail from(Template template, Long userId) {
        return TemplateDetail.builder()
                .templateId(template.getId())
                .author(AuthorDetail.from(template.getAuthor()))
                .title(template.getTitle())
                .content(template.getContent())
                .isAuthor(template.isAuthor(userId))
                .tags(Arrays.asList(template.getTags().split(",")))
                .likeCount(template.getLikeCount())
                .isPrivate(template.isPrivate())
                .build();
    }
}
