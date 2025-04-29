package com.geulowup.backend.domain.user.service;

import com.geulowup.backend.domain.user.dto.request.FolderRequest;
import com.geulowup.backend.domain.user.entity.User;
import com.geulowup.backend.domain.user.exception.UserErrorCode;
import com.geulowup.backend.domain.user.repository.UserRepository;
import com.geulowup.backend.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFolderService {

    private final UserRepository userRepository;

    public void createFolder(Long userId, FolderRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

    }
}
