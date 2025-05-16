package com.javaPlayer.project.model.player;

import com.javaPlayer.project.controller.Controller;
import com.javaPlayer.project.model.entity.SongMetadata;

public interface IMusicPlayer {
    void setController(Controller c);
    void loadAndPlay(String filePath);
    void resume();
    void pause();
    void stop();
    void release();
    void clearRessources();
    long getCurrentPosition();
    long getTotalDuration();
    void seek(long position);
    boolean isPlaying();
    void setVolume(int volume);
    SongMetadata getSongMetadata(String filePath);
    byte[] getSongIcon(String filePath);
}
