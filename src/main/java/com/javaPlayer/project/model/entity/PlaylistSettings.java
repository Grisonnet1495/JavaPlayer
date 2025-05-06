package com.javaPlayer.project.model.entity;

public class PlaylistSettings {
    private String playlistTitle;
    private boolean isDeletingPlaylist;

    public PlaylistSettings(String playlistTitle, boolean isDeletingPlaylist) {
        this.playlistTitle = playlistTitle;
        this.isDeletingPlaylist = isDeletingPlaylist;
    }

    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public boolean isDeletingPlaylist() {
        return isDeletingPlaylist;
    }
}
