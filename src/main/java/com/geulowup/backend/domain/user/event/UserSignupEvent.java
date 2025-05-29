package com.geulowup.backend.domain.user.event;

import com.geulowup.backend.domain.user.entity.User;

public record UserSignupEvent(
        User user
) {
}
