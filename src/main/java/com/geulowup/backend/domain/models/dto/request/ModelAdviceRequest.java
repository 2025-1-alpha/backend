package com.geulowup.backend.domain.models.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ModelAdviceRequest(
        @Schema(description = "조언받을 내용 (본문)", example = "본문 내용입니다. 블라블라~")
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
        List<String> tags
) {
}
