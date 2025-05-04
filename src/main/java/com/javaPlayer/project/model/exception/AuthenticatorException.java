package com.javaPlayer.project.model.exception;

public class AuthenticatorException extends RuntimeException {
    public AuthenticatorException(String message) {
        super(message);
    }

    public AuthenticatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
