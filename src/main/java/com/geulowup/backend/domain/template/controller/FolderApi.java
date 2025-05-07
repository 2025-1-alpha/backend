package com.geulowup.backend.domain.template.controller;

import com.geulowup.backend.domain.template.dto.request.FolderRequest;
import com.geulowup.backend.domain.template.dto.response.FolderDetail;
import com.geulowup.backend.domain.template.dto.response.FolderFindAllResponse;
import com.geulowup.backend.global.security.oauth2.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Folder", description = "템플릿 폴더 API")
public interface FolderApi {
    @Operation(summary = "폴더 생성", description = "폴더를 새로 생성하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "폴더 생성 성공"
                    )
            }
    )
    ResponseEntity<Void> createFolder(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @RequestBody FolderRequest request
    );

    @Operation(summary = "사용자의 폴더 전체 조회", description = "로그인한 사용자의 모든 폴더를 조회하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공"
                    )
            }
    )
    ResponseEntity<FolderFindAllResponse> getAllFolders(
            @AuthenticationPrincipal CustomOAuth2User principal
    );

    @Operation(summary = "폴더 상세 조회", description = "폴더를 상세 조회하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공"
                    )
            }
    )
    ResponseEntity<FolderDetail> getFolderById(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long folderId
    );

    @Operation(summary = "폴더 이름 수정", description = "폴더 이름을 수정하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "이름 수정 성공"
                    )
            }
    )
    ResponseEntity<Void> updateFolderName(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long folderId,
            @RequestBody FolderRequest request
    );

    @Operation(summary = "폴더 삭제", description = "폴더를 삭제하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "삭제 성공"
                    )
            }
    )
    ResponseEntity<Void> deleteFolder(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long folderId
    );
}
