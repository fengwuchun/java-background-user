package com.demo.backend.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demo.backend.common.result.HttpResult;
import com.demo.backend.user.exception.CaptchaExpiredException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CaptchaExpiredException.class)
    public ResponseEntity<HttpResult<?>> handleCaptchaExpired(CaptchaExpiredException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(HttpResult.error(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpResult<?>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(HttpResult.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResult<?>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpResult.error(ex.getMessage()));
    }
}
