package com.geulowup.backend.domain.template.service;

import com.geulowup.backend.domain.template.dto.TemplateFindAllResponse;
import com.geulowup.backend.domain.template.dto.TemplateRequest;
import com.geulowup.backend.domain.template.dto.TemplateSummary;
import com.geulowup.backend.domain.template.entity.Template;
import com.geulowup.backend.domain.template.exception.TemplateErrorCode;
import com.geulowup.backend.domain.template.repository.TemplateRepository;
import com.geulowup.backend.domain.user.entity.User;
import com.geulowup.backend.domain.user.exception.UserErrorCode;
import com.geulowup.backend.domain.user.repository.UserRepository;
import com.geulowup.backend.global.exception.ApiException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createTemplate(Long userId, TemplateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        Template template = Template
                .builder()
                .author(user)
                .title(request.title())
                .content(request.content())
                .likeCount(request.likeCount())
                .isPrivate(request.isPrivate())
                .keywords(String.join(",", request.keywords()))
                .build();

        templateRepository.save(template);
    }

    @Transactional
    public void updateTemplate(Long userId, Long templateId, TemplateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ApiException(TemplateErrorCode.TEMPLATE_NOT_FOUND));

        template.updateTemplate(
                request.title(),
                request.content(),
                request.likeCount(),
                request.keywords(),
                request.isPrivate()
        );
        templateRepository.save(template);
    }

    public TemplateFindAllResponse getAllTemplates() {
        List<TemplateSummary> templates = templateRepository.findAll()
                .stream()
                .map(TemplateSummary::from)
                .toList();

        return TemplateFindAllResponse.builder()
                .templates(templates)
                .build();
    }
}
