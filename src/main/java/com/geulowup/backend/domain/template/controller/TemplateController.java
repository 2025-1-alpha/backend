package com.geulowup.backend.domain.template.controller;

import com.geulowup.backend.domain.template.dto.response.TemplateAuthorInfoResponse;
import com.geulowup.backend.domain.template.dto.response.TemplateDetail;
import com.geulowup.backend.domain.template.dto.response.TemplateFindAllResponse;
import com.geulowup.backend.domain.template.dto.request.TemplateRequest;
import com.geulowup.backend.domain.template.dto.request.TemplateSaveRequest;
import com.geulowup.backend.domain.template.service.TemplateService;
import com.geulowup.backend.global.security.oauth2.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplateController implements TemplateApi {
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
    public ResponseEntity<TemplateFindAllResponse> getAllTemplates(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String tag,
            @PageableDefault(page = 10, sort  ="createdAt", direction = Direction.DESC) @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(templateService.getAllTemplates(search, tag, pageable));
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

    @GetMapping("/me/likes")
    public ResponseEntity<TemplateFindAllResponse> getLikesTemplates(
            @AuthenticationPrincipal CustomOAuth2User principal
    ) {
        return ResponseEntity.ok(templateService.getLikesTemplates(principal.getUserId()));
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
            @AuthenticationPrincipal CustomOAuth2User principal,
            @RequestBody TemplateSaveRequest request
    ) {
        templateService.saveTemplate(principal.getUserId(), templateId, request);
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

    @PostMapping("/{templateId}/likes")
    public ResponseEntity<Void> likeTemplate(
            @AuthenticationPrincipal CustomOAuth2User principal,
            @PathVariable Long templateId
    ) {
        templateService.likeTemplate(principal.getUserId(), templateId);
        return ResponseEntity.ok().build();
    }
}
