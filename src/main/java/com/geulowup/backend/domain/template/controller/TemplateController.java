package com.geulowup.backend.domain.template.controller;

import com.geulowup.backend.domain.template.dto.TemplateAuthorInfoResponse;
import com.geulowup.backend.domain.template.dto.TemplateDetail;
import com.geulowup.backend.domain.template.dto.TemplateFindAllResponse;
import com.geulowup.backend.domain.template.dto.TemplateRequest;
import com.geulowup.backend.domain.template.dto.TemplateSaveRequest;
import com.geulowup.backend.domain.template.service.TemplateService;
import com.geulowup.backend.global.security.oauth2.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    @PostMapping
    public ResponseEntity<Void> createTemplate(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @RequestBody TemplateRequest request
    ) {
        templateService.createTemplate(principal.getUserId(), request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{templateId}")
    public ResponseEntity<Void> updateTemplate(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long templateId,
            @RequestBody TemplateRequest request
    ) {
        templateService.updateTemplate(principal.getUserId(), templateId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<Void> deleteTemplateById(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long templateId
    ) {
        templateService.deleteTemplateById(principal.getUserId(), templateId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<TemplateFindAllResponse> getAllTemplates() {
        return ResponseEntity.ok(templateService.getAllTemplates());
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<TemplateDetail> getTemplateById(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long templateId
    ) {
        return ResponseEntity.ok(templateService.getTemplateById(principal.getUserId(), templateId));
    }

    @GetMapping("/recommendation")
    public ResponseEntity<TemplateFindAllResponse> getRecommendedTemplates(
            @RequestParam(required = false, defaultValue = "false") boolean summary
    ) {
        return ResponseEntity.ok(templateService.getRecommendedTemplates(summary));
    }

    @GetMapping("/{templateId}/authors")
    public ResponseEntity<TemplateAuthorInfoResponse> getTemplateAuthorInfo(
            @PathVariable Long templateId
    ) {
        return ResponseEntity.ok(templateService.getTemplateAuthorInfo(templateId));
    }


    @PostMapping("/{templateId}/save")
    public ResponseEntity<Void> saveTemplate(
            @PathVariable Long templateId,
            @RequestParam Long userId,
            @RequestBody TemplateSaveRequest request
    ) {
        templateService.saveTemplate(userId, templateId, request);
        return ResponseEntity.ok().build();
    }



    @PostMapping("/{templateId}/use")
    public ResponseEntity<Void> useTemplate(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long templateId
    ) {
        Long userId = principal.getUserId();
        templateService.useTemplate(userId, templateId);
        return ResponseEntity.ok().build();
    }
}
