package com.javaPlayer.project.model.entity;

import com.javaPlayer.project.model.exception.SongException;
import com.javaPlayer.project.view.IViewMainWindow;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;

import java.io.File;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Song implements Serializable {
//    private static int currentSongId = 0;
    private int id;
    private String title;
    private String artist;
    private String genre;
    private Duration duration;
    private LocalDateTime addedDate;
    private String filename;
    private IViewMainWindow view;

    public Song(String title, String artist, String genre, Duration duration, LocalDateTime addedDate, String filename) {
        this.id = 0;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.addedDate = addedDate;
        setFilename(filename);
    }

    public Song(Song song) {
        this.id = song.getId();
        this.title = song.getTitle();
        this.artist = song.getArtist();
        this.genre = song.getGenre();
        this.duration = song.getDuration();
        this.addedDate = song.getAddedDate();
        this.filename = song.getFilename();
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
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

    public String getFormattedDuration() {
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

        File songFile = new File(filename);

        if (!songFile.exists()) {
            throw new SongException("File doesn't exist !");
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
        return id == song.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
