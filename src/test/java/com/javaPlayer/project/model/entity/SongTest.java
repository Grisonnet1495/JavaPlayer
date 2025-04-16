package com.javaPlayer.project.model.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SongTest {
    @Test
    void getId() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        assertEquals(1, song.getId());
    }

    @Test
    void setId() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        song.setId(2);
        assertEquals(2, song.getId());
    }

    @Test
    void getTitle() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        assertEquals("The Grid", song.getTitle());
    }

    @Test
    void setTitle() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        song.setTitle("C.L.U");
        assertEquals("C.L.U", song.getTitle());
    }

    @Test
    void getArtist() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        assertEquals(new Artist(1, "Daft Punk"), song.getArtist());
    }

    @Test
    void setArtist() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        song.setArtist(new Artist(2, "Vinsfeld"));
        assertEquals(new Artist(2, "Vinsfeld"), song.getArtist());
    }

    @Test
    void getGenre() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        assertEquals("Soundtrack", song.getGenre());
    }

    @Test
    void setGenre() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        song.setGenre("classic");
        assertEquals("classic", song.getGenre());
    }

    @Test
    void getDuration() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        assertEquals(180, song.getDuration());
    }

    @Test
    void setDuration() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        song.setDuration(190);
        assertEquals(190, song.getDuration());
    }

    @Test
    void getAddedDate() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        assertEquals(new Date(), song.getAddedDate());
    }

    @Test
    void setAddedDate() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        song.setAddedDate(new Date());
        assertEquals(new Date(), song.getAddedDate());
    }

    @Test
    void testToString() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        Song song2 = new Song(2, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        assertEquals(song.toString(), song2.toString());
    }
}
