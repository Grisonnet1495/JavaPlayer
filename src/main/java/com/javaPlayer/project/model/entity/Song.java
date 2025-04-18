package com.javaPlayer.project.model.entity;

import java.util.Date;

public class Song {
//    private static int currentSongId = 0;
    private int id;
    private String title;
    private Artist artist;
    private String genre;
    private int duration;
    private Date addedDate;

    public Song() {
        this.title = "Unknown Title";
        this.artist = null;
        this.genre = "Unknown genre";
        this.duration = 0;
        this.addedDate = new Date();
    }

    public Song(int id, String title, Artist artist, String genre, int duration, Date addedDate) {
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
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
}
