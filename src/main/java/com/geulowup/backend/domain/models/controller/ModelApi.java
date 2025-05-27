package com.geulowup.backend.domain.models.controller;

import com.geulowup.backend.domain.models.dto.request.ModelAdviceRequest;
import com.geulowup.backend.domain.models.dto.request.ModelPlaceholderRequest;
import com.geulowup.backend.domain.models.dto.request.ModelSpellCheckRequest;
import com.geulowup.backend.domain.models.dto.response.ModelResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "AI Model", description = "AI 활용 API")
public interface ModelApi {
    @Operation(summary = "AI를 활용한 글 조언받기", description = "Gemini Rest API를 활용하여 글의 내용을 첨삭/조언받는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조언받기 성공"
                    )
            }
    )
    ResponseEntity<ModelResponse> getModelAdvice(@RequestBody ModelAdviceRequest request);

    @Operation(summary = "AI를 활용한 글 빈 칸 뚫기", description = "Gemini Rest API를 활용하여 글에 적절한 빈 칸을 뚫어주는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "빈 칸 뚫기 성공"
                    )
            }
    )
    ResponseEntity<ModelResponse> getModelPlaceholderResults(@RequestBody ModelPlaceholderRequest request);

    @Operation(summary = "맞춤법 검사", description = "맞춤법 검사 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "맞춤법 검사 성공"
                    )
            }
    )
    ResponseEntity<ModelResponse> getSpellCheckResults(@RequestBody ModelSpellCheckRequest request);
}
