package me.elyor.memberservice.global.error;

import me.elyor.memberservice.global.error.exception.ErrorCode;
import me.elyor.memberservice.global.error.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    ResponseEntity<ErrorResponse> handleGlobalException(GlobalException exception) {
        log.error("handleGlobalException", exception);
        ErrorResponse errorResponse = exception.getErrorResponse();
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("handleHttpMessageNotReadableException", e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
        return buildResponseEntity(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity<ErrorResponse> handleMissingRequestParamException(
            MissingServletRequestParameterException e) {
        log.error("handleMissingRequestParamException", e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.MISSING_REQUEST_PARAM);
        return buildResponseEntity(response);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("handleDuplicateKeyException", e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.DUPLICATE_RESOURCE);
        return buildResponseEntity(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("handleNoHandlerFoundException", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NO_HANDLER_FOUND);
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);
        e.getBindingResult().getFieldErrors();
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleException", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return buildResponseEntity(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(ErrorResponse response) {
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
