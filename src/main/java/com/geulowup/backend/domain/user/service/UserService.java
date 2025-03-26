package com.geulowup.backend.domain.user.service;

import com.geulowup.backend.domain.user.dto.CurrentUserInfoResponse;
import com.geulowup.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public CurrentUserInfoResponse getCurrentUser(Long userId) {

    }
}
