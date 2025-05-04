package com.javaPlayer.project.model.exception;

public class PlaylistException extends RuntimeException {
    public PlaylistException(String message) {
        super(message);
    }

    public PlaylistException(String message, Throwable cause) {
        super(message, cause);
    }
}
