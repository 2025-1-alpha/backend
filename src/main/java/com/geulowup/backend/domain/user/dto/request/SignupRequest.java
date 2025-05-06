package com.geulowup.backend.domain.user.dto.request;

import java.util.List;

public record SignupRequest(
        String name,
        String job,
        List<String> tags
) {
}
