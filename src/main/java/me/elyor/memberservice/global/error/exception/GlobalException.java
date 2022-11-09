package me.elyor.memberservice.global.error.exception;

import me.elyor.memberservice.global.error.ErrorResponse;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private ErrorResponse errorResponse;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorResponse = ErrorResponse.of(errorCode);
    }

}
