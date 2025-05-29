package com.geulowup.backend.domain.user.service;

import com.geulowup.backend.domain.user.dto.request.SignupRequest;
import com.geulowup.backend.domain.user.dto.response.CurrentUserInfoResponse;
import com.geulowup.backend.domain.user.dto.request.UserNicknameUpdateRequest;
import com.geulowup.backend.domain.user.entity.User;
import com.geulowup.backend.domain.user.event.UserSignupEvent;
import com.geulowup.backend.domain.user.exception.UserErrorCode;
import com.geulowup.backend.domain.user.repository.UserRepository;
import com.geulowup.backend.global.exception.ApiException;
import com.geulowup.backend.global.external.s3.S3Service;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final ApplicationEventPublisher eventPublisher;


    //내 정보 조회
    public CurrentUserInfoResponse getCurrentUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        return CurrentUserInfoResponse.from(user);
    }

    @Transactional
    public void updateNickname(Long userId, UserNicknameUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        user.updateNickname(request.nickname());
    }


    @Transactional
    public void updateProfileImage(Long userId, MultipartFile image) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        if (image == null) {
            user.updateProfileImageUrl(null);
            return;
        }

        String uploadedUrl = uploadProfileImage(userId, image);

        user.updateProfileImageUrl(uploadedUrl);
    }

    private String uploadProfileImage(Long userId, MultipartFile file) {
        String contentType = Objects.requireNonNull(file.getContentType());

        if (!contentType.contains("image")) {
            throw new ApiException(UserErrorCode.INVALID_PROFILE_IMAGE_FILE_TYPE);
        }

        String newFileDir = "profiles/" + userId;

        return s3Service.uploadFile(file, newFileDir);
    }

    @Transactional
    public void signUp(Long userId, SignupRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        user.signUp(request.job(), request.tags());
        eventPublisher.publishEvent(new UserSignupEvent(user));
    }


    //회원 탈퇴
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
