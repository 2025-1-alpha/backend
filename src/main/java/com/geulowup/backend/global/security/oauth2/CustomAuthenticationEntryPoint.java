package com.geulowup.backend.global.security.oauth2;

import com.geulowup.backend.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geulowup.backend.global.exception.GlobalErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        returnErrorResponse(response, authException);
    }

    private void returnErrorResponse(HttpServletResponse response, Exception e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse error = ErrorResponse.from(GlobalErrorCode.INTERNAL_SERVER_ERROR, Instant.now());
        response.getWriter().write(objectMapper.writeValueAsString(error));
        System.out.println(error.getMessage());
    }
}