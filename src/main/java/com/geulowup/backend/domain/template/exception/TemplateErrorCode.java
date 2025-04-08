package com.geulowup.backend.domain.template.exception;

import com.geulowup.backend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum TemplateErrorCode implements ErrorCode {
    TEMPLATE_NOT_FOUND("템플릿을 찾을 수 없습니다", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;
}
