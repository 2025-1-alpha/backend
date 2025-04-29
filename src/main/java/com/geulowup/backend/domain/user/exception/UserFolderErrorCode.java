package com.geulowup.backend.domain.user.exception;

import com.geulowup.backend.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserFolderErrorCode implements ErrorCode {
    FOLDER_NOT_FOUND("사용자의 폴더를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FOLDER_NAME_DUPLICATE("폴더 이름이 중복되었습니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;
}
