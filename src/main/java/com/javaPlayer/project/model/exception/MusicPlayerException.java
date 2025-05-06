package com.javaPlayer.project.model.exception;

import com.javaPlayer.project.model.player.MusicPlayer;

public class MusicPlayerException extends RuntimeException {
    public MusicPlayerException(String message) {
        super(message);
    }

    public MusicPlayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
