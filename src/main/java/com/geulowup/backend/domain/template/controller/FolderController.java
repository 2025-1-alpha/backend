package com.geulowup.backend.domain.template.controller;

import com.geulowup.backend.domain.template.dto.request.FolderRequest;
import com.geulowup.backend.domain.template.dto.response.FolderDetail;
import com.geulowup.backend.domain.template.dto.response.FolderFindAllResponse;
import com.geulowup.backend.domain.template.service.FolderService;
import com.geulowup.backend.global.security.oauth2.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<Void> createFolder(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @RequestBody FolderRequest request
    ) {
        folderService.createFolder(principal.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<FolderFindAllResponse> getAllFolders(
            @AuthenticationPrincipal CustomOAuth2User principal
    ) {
        return ResponseEntity.ok(folderService.getAllFolders(principal.getUserId()));
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<FolderDetail> getFolderById(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long folderId
    ) {
        return ResponseEntity.ok(folderService.getFolderById(principal.getUserId(), folderId));
    }

    @PatchMapping("/{folderId}/names")
    public ResponseEntity<Void> updateFolderName(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long folderId,
            @RequestBody FolderRequest request
    ) {
        folderService.updateFolderName(principal.getUserId(), folderId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteFolder(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long folderId
    ) {
        folderService.deleteFolder(principal.getUserId(), folderId);
        return ResponseEntity.ok().build();
    }
}
