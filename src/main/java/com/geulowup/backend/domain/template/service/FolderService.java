package com.geulowup.backend.domain.template.service;

import com.geulowup.backend.domain.template.dto.request.FolderRequest;
import com.geulowup.backend.domain.template.dto.response.FolderDetail;
import com.geulowup.backend.domain.template.dto.response.FolderFindAllResponse;
import com.geulowup.backend.domain.template.dto.response.FolderSummary;
import com.geulowup.backend.domain.template.entity.Template;
import com.geulowup.backend.domain.template.entity.UserTemplate;
import com.geulowup.backend.domain.template.entity.UserTemplateFolder;
import com.geulowup.backend.domain.template.exception.UserFolderErrorCode;
import com.geulowup.backend.domain.template.repository.UserFolderRepository;
import com.geulowup.backend.domain.template.repository.UserTemplateRepository;
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
public class FolderService {

    private final UserRepository userRepository;
    private final UserFolderRepository userFolderRepository;
    private final UserTemplateRepository userTemplateRepository;

    @Transactional
    public void createFolder(Long userId, FolderRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
        String folderName = request.name();

        if (userFolderRepository.existsByUserAndName(user, folderName)) {
            throw new ApiException(UserFolderErrorCode.FOLDER_NAME_DUPLICATE);
        }

        UserTemplateFolder folder = UserTemplateFolder
                .builder()
                .user(user)
                .name(folderName)
                .build();

        userFolderRepository.save(folder);
    }

    public FolderFindAllResponse getAllFolders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        List<FolderSummary> folders = userFolderRepository.findAllByUser(user)
                .stream()
                .map(FolderSummary::from)
                .toList();

        return FolderFindAllResponse
                .builder()
                .folders(folders)
                .build();
    }

    @Transactional
    public void updateFolderName(Long userId, Long folderId, FolderRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        UserTemplateFolder folder = userFolderRepository.findById(folderId)
                .orElseThrow(() -> new ApiException(UserFolderErrorCode.FOLDER_NOT_FOUND));

        if (!folder.canAccess(user)) {
            throw new ApiException(UserFolderErrorCode.FOLDER_ACCESS_DENIED);
        }

        folder.updateName(request.name());
    }

    @Transactional
    public void deleteFolder(Long userId, Long folderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        UserTemplateFolder folder = userFolderRepository.findById(folderId)
                .orElseThrow(() -> new ApiException(UserFolderErrorCode.FOLDER_NOT_FOUND));

        if (!folder.canAccess(user)) {
            throw new ApiException(UserFolderErrorCode.FOLDER_ACCESS_DENIED);
        }

        userFolderRepository.delete(folder);
    }

    public FolderDetail getFolderById(Long userId, Long folderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        UserTemplateFolder folder = userFolderRepository.findById(folderId)
                .orElseThrow(() -> new ApiException(UserFolderErrorCode.FOLDER_NOT_FOUND));

        if (!folder.canAccess(user)) {
            throw new ApiException(UserFolderErrorCode.FOLDER_ACCESS_DENIED);
        }

        List<Template> templates = userTemplateRepository.findAllByFolder(folder)
                .stream()
                .map(UserTemplate::getTemplate)
                .toList();

        return FolderDetail.from(folder, templates);
    }
}
