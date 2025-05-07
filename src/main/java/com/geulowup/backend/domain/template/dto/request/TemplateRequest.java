package com.geulowup.backend.domain.template.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record TemplateRequest(

        @Schema(description = "제목", example = "정중한 사과문 작성법")
        String title,

        @Schema(description = "본문", example = "불편을 드려 죄송합니다. 본문에는 진심 어린 사과의 말을 담습니다...")
        String content,

        @Schema(description = "추천 수", example = "7")
        int likeCount,

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

        @Schema(description = "공개 여부 (true: 비공개, false: 공개)", example = "false")
        boolean isPrivate
) {

}
