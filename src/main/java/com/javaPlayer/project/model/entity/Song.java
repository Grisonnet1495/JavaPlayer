package com.javaPlayer.project.model.entity;

import com.javaPlayer.project.model.exception.SongException;

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
    private String filename;

    public Song(String title, Artist artist, String genre, Duration duration, LocalDateTime addedDate, String filename) {
        this.id = 0;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.addedDate = addedDate;
        this.filename = filename;
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
        if (duration == null) {
            return "00:00";
        }

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        if (hours <= 0) {
            // Without hours
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            // With hours
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        }
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new SongException("Filename can't be empty !");
        }

        this.filename = filename;
    }

    public String getSongFileExtension() {
        int lastDot = filename.lastIndexOf('.');

        if (lastDot > 0 && lastDot < filename.length() - 1) {
            return filename.substring(lastDot + 1);
        }

        return "";
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
                ", filename='" + filename + '\'' +
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
