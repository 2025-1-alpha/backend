package com.geulowup.backend.domain.template.service;

import com.geulowup.backend.domain.template.dto.response.AuthorDetail;
import com.geulowup.backend.domain.template.dto.response.TemplateAuthorInfoResponse;
import com.geulowup.backend.domain.template.dto.response.TemplateDetail;
import com.geulowup.backend.domain.template.dto.response.TemplateFindAllResponse;
import com.geulowup.backend.domain.template.dto.request.TemplateRequest;
import com.geulowup.backend.domain.template.dto.request.TemplateSaveRequest;
import com.geulowup.backend.domain.template.dto.response.TemplateSummary;
import com.geulowup.backend.domain.template.entity.Template;
import com.geulowup.backend.domain.template.entity.TemplateLike;
import com.geulowup.backend.domain.template.entity.TemplateLikeId;
import com.geulowup.backend.domain.template.entity.UserTemplate;
import com.geulowup.backend.domain.template.entity.UserTemplateHistory;
import com.geulowup.backend.domain.template.exception.TemplateErrorCode;
import com.geulowup.backend.domain.template.repository.TemplateLikeRepository;
import com.geulowup.backend.domain.template.repository.TemplateRepository;
import com.geulowup.backend.domain.template.repository.UserTemplateRepository;
import com.geulowup.backend.domain.user.entity.User;
import com.geulowup.backend.domain.template.entity.UserTemplateFolder;
import com.geulowup.backend.domain.user.exception.UserErrorCode;
import com.geulowup.backend.domain.template.repository.UserFolderRepository;
import com.geulowup.backend.domain.template.repository.UserHistoryRepository;
import com.geulowup.backend.domain.user.repository.UserRepository;
import com.geulowup.backend.global.exception.ApiException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final TemplateLikeRepository templateLikeRepository;

    @Transactional
    public void createTemplate(Long userId, TemplateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        Template template = Template
                .builder()
                .author(user)
                .title(request.title())
                .content(request.content())
                .isPrivate(request.isPrivate())
                .likeCount(0)
                .tags(String.join(",", request.tags()))
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
                request.tags(),
                request.isPrivate()
        );
    }

    public TemplateFindAllResponse getAllTemplates(String search, String tag, Pageable pageable) {

        Page<Template> results = templateRepository.findAllByFiltering(search, tag, pageable);

        List<TemplateSummary> templates = results
                .stream()
                .map(TemplateSummary::from)
                .toList();

        return TemplateFindAllResponse.builder()
                .templates(templates)
                .totalPage(results.getTotalPages())
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

        boolean saved = false;
        UserTemplateFolder savedFolder = null;

        if (userId != null) {
            Optional<UserTemplate> userTemplateOpt = userTemplateRepository
                    .findByFolderUserIdAndTemplateId(userId, templateId);

            if (userTemplateOpt.isPresent()) {
                UserTemplate userTemplate = userTemplateOpt.get();
                saved = true;
                savedFolder = userTemplate.getFolder();
            }
        }

        return TemplateDetail.from(template, userId, saved, savedFolder);
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
                .totalPage(1)
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

        if (!folder.canAccess(user)) {
            throw new ApiException(TemplateErrorCode.TEMPLATE_ACCESS_DENIED);
        }

        UserTemplate userTemplate = UserTemplate.builder()
                .folder(folder)
                .template(template)
                .build();

        userTemplateRepository.save(userTemplate);
    }


    @Transactional
    public void useTemplate(Long userId, Long templateId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ApiException(TemplateErrorCode.TEMPLATE_NOT_FOUND));

        UserTemplateHistory history = UserTemplateHistory.builder()
                .user(user)
                .template(template)
                .build();

        userHistoryRepository.save(history);
    }

    @Transactional
    public void likeTemplate(Long userId, Long templateId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ApiException(TemplateErrorCode.TEMPLATE_NOT_FOUND));

        if (templateLikeRepository.existsByTemplateAndUser(template, user)) {

            template.removeLike();
            templateLikeRepository.deleteByTemplateAndUser(template, user);
            return;
        }

        template.addLike();

        TemplateLike templateLike = TemplateLike.builder()
                .id(new TemplateLikeId(templateId, userId))
                .template(template)
                .user(user)
                .build();

        templateLikeRepository.save(templateLike);
    }

    public TemplateFindAllResponse getLikesTemplates(Long userId) {
        List<Template> templates = templateLikeRepository.findAllByUserId(userId)
                .stream()
                .map(TemplateLike::getTemplate)
                .toList();

        List<TemplateSummary> summaries = templates
                .stream()
                .map(TemplateSummary::from)
                .toList();

        return TemplateFindAllResponse
                .builder()
                .templates(summaries)
                .totalPage(1)
                .build();
    }

    public TemplateFindAllResponse getCurrentUserTemplates(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        List<Template> currentUserTemplates = templateRepository.findAllByAuthorOrderByLikeCountDesc(user);

        List<TemplateSummary> summaries = currentUserTemplates
                .stream()
                .map(TemplateSummary::from)
                .toList();

        return TemplateFindAllResponse
                .builder()
                .templates(summaries)
                .totalPage(1)
                .build();
    }
}
