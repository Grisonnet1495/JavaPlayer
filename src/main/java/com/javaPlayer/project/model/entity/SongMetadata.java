package com.javaPlayer.project.model.entity;

public class SongMetadata {
    String title;
    String artist;
    String album;
    String genre;
    byte[] songIcon;

    public SongMetadata(String title, String artist, String album, String genre, byte[] icon) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.songIcon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    public byte[] getSongIcon() {
        return songIcon;
    }
}
