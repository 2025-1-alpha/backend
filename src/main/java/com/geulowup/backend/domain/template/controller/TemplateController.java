package com.geulowup.backend.domain.template.controller;

import com.geulowup.backend.domain.template.dto.TemplateFindAllResponse;
import com.geulowup.backend.domain.template.dto.TemplateRequest;
import com.geulowup.backend.domain.template.service.TemplateService;
import com.geulowup.backend.domain.user.service.UserService;
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
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createTemplate(@AuthenticationPrincipal CustomOAuth2User principal, @RequestBody TemplateRequest request) {
        templateService.createTemplate(principal.getUserId(), request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{templateId}")
    public ResponseEntity<Void> updateTemplate(@AuthenticationPrincipal CustomOAuth2User principal, @PathVariable Long templateId, @RequestBody TemplateRequest request) {
        templateService.updateTemplate(principal.getUserId(), templateId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<TemplateFindAllResponse> getAllTemplates() {
        return ResponseEntity.ok(templateService.getAllTemplates());
    }


}
