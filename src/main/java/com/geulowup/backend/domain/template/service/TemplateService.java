package com.geulowup.backend.domain.template.service;

import com.geulowup.backend.domain.template.dto.AuthorDetail;
import com.geulowup.backend.domain.template.dto.TemplateAuthorInfoResponse;
import com.geulowup.backend.domain.template.dto.TemplateDetail;
import com.geulowup.backend.domain.template.dto.TemplateFindAllResponse;
import com.geulowup.backend.domain.template.dto.TemplateRequest;
import com.geulowup.backend.domain.template.dto.TemplateSaveRequest;
import com.geulowup.backend.domain.template.dto.TemplateSummary;
import com.geulowup.backend.domain.template.entity.Template;
import com.geulowup.backend.domain.template.entity.UserTemplate;
import com.geulowup.backend.domain.template.entity.UserTemplateHistory;
import com.geulowup.backend.domain.template.exception.TemplateErrorCode;
import com.geulowup.backend.domain.template.repository.TemplateRepository;
import com.geulowup.backend.domain.template.repository.UserTemplateRepository;
import com.geulowup.backend.domain.user.entity.User;
import com.geulowup.backend.domain.user.entity.UserTemplateFolder;
import com.geulowup.backend.domain.user.exception.UserErrorCode;
import com.geulowup.backend.domain.user.repository.UserFolderRepository;
import com.geulowup.backend.domain.user.repository.UserHistoryRepository;
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
    private final UserFolderRepository userFolderRepository;
    private final UserTemplateRepository userTemplateRepository;
    private final UserHistoryRepository userHistoryRepository;

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
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ApiException(TemplateErrorCode.TEMPLATE_NOT_FOUND));

        if (!template.isAuthor(userId)) {
            throw new ApiException(TemplateErrorCode.TEMPLATE_ACCESS_DENIED);
        }

        template.updateTemplate(
                request.title(),
                request.content(),
                request.likeCount(),
                request.keywords(),
                request.isPrivate()
        );
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

    @Transactional
    public void deleteTemplateById(Long userId, Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ApiException(TemplateErrorCode.TEMPLATE_NOT_FOUND));

        if (!template.isAuthor(userId)) {
            throw new ApiException(TemplateErrorCode.TEMPLATE_ACCESS_DENIED);
        }

        templateRepository.delete(template);
    }

    public TemplateDetail getTemplateById(Long userId, Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ApiException(TemplateErrorCode.TEMPLATE_NOT_FOUND));

        return TemplateDetail.from(template, userId);
    }

    public TemplateFindAllResponse getRecommendedTemplates(boolean summary) {
        List<Template> templates = (summary) ? templateRepository.findTop5ByOrderByLikeCountDesc()
                : templateRepository.findAllByOrderByLikeCountDesc();

        return TemplateFindAllResponse
                .builder()
                .templates(
                        templates.stream()
                                .map(TemplateSummary::from)
                                .toList()
                )
                .build();
    }

    public TemplateAuthorInfoResponse getTemplateAuthorInfo(Long templateId) {
        Template currentTemplate = templateRepository.findById(templateId)
                .orElseThrow(() -> new ApiException(TemplateErrorCode.TEMPLATE_NOT_FOUND));
        List<Template> templates = templateRepository.findAllByAuthorOrderByLikeCountDesc(
                currentTemplate.getAuthor());

        return TemplateAuthorInfoResponse.builder()
                .author(AuthorDetail.from(currentTemplate.getAuthor()))
                .templateTotalCount(templates.size())
                .templates(templates
                        .stream()
                        .map(TemplateSummary::from)
                        .toList()
                )
                .build();
    }


    @Transactional
    public void saveTemplate(Long userId, Long templateId, TemplateSaveRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ApiException(TemplateErrorCode.TEMPLATE_NOT_FOUND));
        UserTemplateFolder folder = userFolderRepository.findById(request.folderId())
                .orElseThrow(() -> new ApiException(TemplateErrorCode.TEMPLATE_NOT_FOUND));

        UserTemplate userTemplate = UserTemplate.builder()
                .folder(folder)
                .template(template)
                .build();

        userTemplateRepository.save(userTemplate);
    }


    @Transactional
    public void useTemplate(Long userTemplateId) {
        UserTemplate userTemplate = userTemplateRepository.findById(userTemplateId)
                .orElseThrow(() -> new ApiException(TemplateErrorCode.TEMPLATE_NOT_FOUND));

        UserTemplateHistory history = UserTemplateHistory.builder()
                .user(userTemplate.getUser())
                .template(userTemplate.getTemplate())
                .build();

        userHistoryRepository.save(history);


    }
}
