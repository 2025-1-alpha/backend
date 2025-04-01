package com.geulowup.backend.domain.user.service;

import com.geulowup.backend.domain.user.dto.CurrentUserInfoResponse;
import com.geulowup.backend.domain.user.dto.UserNicknameUpdateRequest;
import com.geulowup.backend.domain.user.entity.User;
import com.geulowup.backend.domain.user.exception.UserErrorCode;
import com.geulowup.backend.domain.user.repository.UserRepository;
import com.geulowup.backend.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


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


}
