package com.demo.backend.user.exception;

public class CaptchaExpiredException extends RuntimeException {
    public CaptchaExpiredException(String message) {
        super(message);
    }
}
