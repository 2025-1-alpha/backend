package com.geulowup.backend.domain.template.exception;

import com.geulowup.backend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserFolderErrorCode implements ErrorCode {
    FOLDER_NOT_FOUND("사용자의 폴더를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FOLDER_NAME_DUPLICATE("폴더 이름이 중복되었습니다.", HttpStatus.CONFLICT),
    FOLDER_ACCESS_DENIED("폴더에 접근할 수 있는 권한이 없습니다.", HttpStatus.FORBIDDEN),
    FOLDER_TEMPLATE_DUPLICATE("템플릿은 하나의 폴더에만 저장할 수 있습니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;
}
