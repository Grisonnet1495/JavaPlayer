package com.javaPlayer.project.model.entity;

public class SongDetails {
    private String songTitle;
    private String songArtist;

    public SongDetails(String songTitle, String songArtist) {
        this.songTitle = songTitle;
        this.songArtist = songArtist;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }
}
