package com.javaPlayer.project.model.entity;

public class PlaylistSettings {
    private String playlistName;
    private boolean isDeletingPlaylist;

    public PlaylistSettings(String playlistName, boolean isDeletingPlaylist) {
        this.playlistName = playlistName;
        this.isDeletingPlaylist = isDeletingPlaylist;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public boolean isDeletingPlaylist() {
        return isDeletingPlaylist;
    }
}
