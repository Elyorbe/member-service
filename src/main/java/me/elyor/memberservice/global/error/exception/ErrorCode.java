package me.elyor.memberservice.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NO_HANDLER_FOUND("No handler found for request", HttpStatus.NOT_FOUND.value()),
    METHOD_NOT_ALLOWED("Method Not Allowed", HttpStatus.METHOD_NOT_ALLOWED.value()),
    INTERNAL_SERVER_ERROR("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value()),

    DUPLICATE_USERNAME("동일한 사용자 아이디가 이미 존재합니다", HttpStatus.CONFLICT.value()),
    DUPLICATE_RESOURCE("리소스가 이미 존재합니다. 다른 값을 사용해 보세요", HttpStatus.CONFLICT.value()),
    INVALID_INPUT_VALUE("입력 값이 잘못되었습니다", HttpStatus.BAD_REQUEST.value()),
    MISSING_REQUEST_PARAM("필수 요청 파라미터가 없습니다", HttpStatus.BAD_REQUEST.value()),
    RESOURCE_NOT_FOUND("요청한 리소스가 없습니다",HttpStatus.NOT_FOUND.value());

    private final String message;
    private final int status;

    ErrorCode(String message, int status) {
        this.status = status;
        this.message = message;
    }

}
