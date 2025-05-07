package com.geulowup.backend.domain.user.controller;

import com.geulowup.backend.domain.user.dto.request.SignupRequest;
import com.geulowup.backend.domain.user.dto.request.UserNicknameUpdateRequest;
import com.geulowup.backend.domain.user.dto.response.CurrentUserInfoResponse;
import com.geulowup.backend.global.security.oauth2.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User", description = "사용자 API")
public interface UserApi {
    @Operation(summary = "회원가입", description = "회원가입 API (직업, 키워드 입력)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원가입 성공"
                    )
            }
    )
    ResponseEntity<Void> signUp(@AuthenticationPrincipal CustomOAuth2User principal, @RequestBody SignupRequest request);

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자 정보를 조회하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공"
                    )
            }
    )
    ResponseEntity<CurrentUserInfoResponse> getCurrentUser(@AuthenticationPrincipal CustomOAuth2User principal);

    @Operation(summary = "이름 변경", description = "현재 로그인한 사용자의 이름을 변경하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "이름 변경 성공"
                    )
            }
    )
    ResponseEntity<Void> updateNickname(@AuthenticationPrincipal CustomOAuth2User principal, @RequestBody UserNicknameUpdateRequest request);

    @Operation(summary = "프로필 이미지 수정", description = "현재 로그인한 사용자의 프로필 이미지를 수정하는 API")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "프로필 이미지 수정 성공"
                    )
            }
    )
    ResponseEntity<Void> updateProfileImage(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @RequestPart(required = false) MultipartFile image
    );
}
