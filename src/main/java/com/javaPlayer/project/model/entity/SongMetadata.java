package com.javaPlayer.project.model.entity;

import org.jaudiotagger.tag.datatype.Artwork;

public class SongMetadata {
    String title;
    String artist;
    String album;
    String genre;
    Artwork albumImage;

    public SongMetadata(String title, String artist, String album, String genre, Artwork albumImage) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.albumImage = albumImage;
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

    public Artwork getAlbumImage() {
        return albumImage;
    }
}
