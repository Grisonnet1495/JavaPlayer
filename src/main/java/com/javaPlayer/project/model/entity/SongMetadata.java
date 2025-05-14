package com.javaPlayer.project.model.entity;

import java.time.Duration;

public class SongMetadata {
    String title;
    String artist;
    String album;
    String genre;
    Duration duration;
    byte[] songIcon;

    public SongMetadata(String title, String artist, String album, String genre, Duration duration, byte[] icon) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = duration;
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

    public Duration getDuration() {return duration;}

    public byte[] getSongIcon() {
        return songIcon;
    }

    @Override
    public String toString() {
        return "SongMetadata{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                '}';
    }
}
