package com.javaPlayer.project.model.player;

import com.javaPlayer.project.model.entity.SongMetadata;

public interface IMusicPlayer {
    void loadAndPlay(String filePath);
    void resume();
    void pause();
    void stop();
    void release();
    boolean isPlaying();
    void setVolume(int volume);
    SongMetadata getSongMetadata(String filePath);
    byte[] getSongIcon(String filePath);
}
