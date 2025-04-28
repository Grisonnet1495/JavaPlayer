package com.javaPlayer.project.model.entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class Song implements Serializable {
//    private static int currentSongId = 0;
    private int id;
    private String title;
    private Artist artist;
    private String genre;
    private Duration duration;
    private LocalDateTime addedDate;

    public Song() {
        this.title = "Unknown Title";
        this.artist = null;
        this.genre = "Unknown genre";
        this.duration = Duration.ZERO;
        this.addedDate = LocalDateTime.now();
    }

    public Song(int id, String title, Artist artist, String genre, Duration duration, LocalDateTime addedDate) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.addedDate = addedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getDurationToString() {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        if (hours == 0) {
            return minutes + ":" + seconds;
        }

        return hours + ":" + minutes + ":" + seconds;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist=" + artist +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", addedDate=" + addedDate +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        return title.equals(song.title) && artist.equals(song.artist) && genre.equals(song.genre) && duration == song.duration;
    }
}
