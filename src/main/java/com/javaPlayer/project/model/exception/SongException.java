package com.javaPlayer.project.model.exception;

public class SongException extends RuntimeException {
    public SongException(String message) {
        super(message);
    }

    public SongException(String message, Throwable cause) {
        super(message, cause);
    }
}
