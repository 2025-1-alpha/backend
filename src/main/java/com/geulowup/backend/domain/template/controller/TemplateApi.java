package com.geulowup.backend.domain.template.controller;


import com.geulowup.backend.domain.template.dto.request.TemplateRequest;
import com.geulowup.backend.domain.template.dto.request.TemplateSaveRequest;
import com.geulowup.backend.domain.template.dto.response.TemplateAuthorInfoResponse;
import com.geulowup.backend.domain.template.dto.response.TemplateDetail;
import com.geulowup.backend.domain.template.dto.response.TemplateFindAllResponse;
import com.geulowup.backend.global.security.oauth2.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Template", description = "템플릿 API")
public interface TemplateApi {
    @Operation(summary = "템플릿 생성", description = "새로운 템플릿을 생성하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "생성 성공"
                    )
            }
    )
    ResponseEntity<Void> createTemplate(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @RequestBody TemplateRequest request
    );

    @Operation(summary = "템플릿 수정", description = "템플릿 내용을 수정하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "수정 성공"
                    )
            }
    )
    ResponseEntity<Void> updateTemplate(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long templateId,
            @RequestBody TemplateRequest request
    );

    @Operation(summary = "템플릿 삭제", description = "템플릿을 삭제하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "삭제 성공"
                    )
            }
    )
    ResponseEntity<Void> deleteTemplateById(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long templateId
    );

    @Operation(summary = "템플릿 전체 조회", description = "모든 템플릿을 조회하는 API (검색어, 태그, 정렬 필터링 가능)"
            + "<br />"
            + "<br />page - 페이지 단위로 잘라서 조회할 때 사용 (0부터 시작)"
            + "<br />size - 한 페이지 당 불러올 개수"
            + "<br />sort - [최신순 (기본): createdAt,desc / 인기순: likeCount,desc]")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "전체 조회 성공"
                    )
            }
    )
    @PageableAsQueryParam
    ResponseEntity<TemplateFindAllResponse> getAllTemplates(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String tag,
            @PageableDefault(sort = "createdAt", direction = Direction.DESC) @ParameterObject Pageable pageable
    );

    @Operation(summary = "템플릿 상세 조회", description = "템플릿 상세 내용을 조회하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공"
                    )
            }
    )
    ResponseEntity<TemplateDetail> getTemplateById(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long templateId
    );

    @Operation(summary = "추천 템플릿 조회", description = "추천 템플릿(추천 수 내림차순)을 조회하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공"
                    )
            }
    )
    ResponseEntity<TemplateFindAllResponse> getRecommendedTemplates(
            @RequestParam(required = false, defaultValue = "false") boolean summary
    );

    @Operation(summary = "찜한 템플릿 조회", description = "찜한 템플릿(내가 추천 누른 템플릿)을 조회하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공"
                    )
            }
    )
    ResponseEntity<TemplateFindAllResponse> getLikesTemplates(
            @AuthenticationPrincipal CustomOAuth2User principal
    );

    @Operation(summary = "템플릿 작성자 조회", description = "템플릿 작성자를 조회하는 모달을 위한 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공"
                    )
            }
    )
    ResponseEntity<TemplateAuthorInfoResponse> getTemplateAuthorInfo(
            @PathVariable Long templateId
    );

    @Operation(summary = "템플릿 저장", description = "템플릿 저장하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "저장 성공"
                    )
            }
    )
    ResponseEntity<Void> saveTemplate(
            @PathVariable Long templateId,
            @AuthenticationPrincipal CustomOAuth2User principal,
            @RequestBody TemplateSaveRequest request
    );

    @Operation(summary = "템플릿 사용", description = "템플릿 사용 기록에 추가하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "사용 기록 저장 성공"
                    )
            }
    )
    ResponseEntity<Void> useTemplate(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long templateId
    );

    @Operation(summary = "템플릿 추천 누르기", description = "템플릿 추천 눌렀을 때 호출하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "추천 누르기 성공"
                    )
            }
    )
    ResponseEntity<Void> likeTemplate(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long templateId
    );
}
