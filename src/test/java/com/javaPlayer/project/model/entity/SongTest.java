package com.javaPlayer.project.model.entity;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SongTest {
    @Test
    void getId() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
        assertEquals(1, song.getId());
    }

    @Test
    void setId() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
        song.setId(2);
        assertEquals(2, song.getId());
    }

    @Test
    void getTitle() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
        assertEquals("The Grid", song.getTitle());
    }

    @Test
    void setTitle() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
        song.setTitle("C.L.U");
        assertEquals("C.L.U", song.getTitle());
    }

    @Test
    void getArtist() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
        assertEquals(new Artist(1, "Daft Punk"), song.getArtist());
    }

    @Test
    void setArtist() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
        song.setArtist(new Artist(2, "Vinsfeld"));
        assertEquals(new Artist(2, "Vinsfeld"), song.getArtist());
    }

    @Test
    void getGenre() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
        assertEquals("Soundtrack", song.getGenre());
    }

    @Test
    void setGenre() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
        song.setGenre("classic");
        assertEquals("classic", song.getGenre());
    }

    @Test
    void getDuration() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
        assertEquals(Duration.ofMinutes(3), song.getDuration());
    }

    @Test
    void setDuration() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
        song.setDuration(Duration.ofMinutes(3));
        assertEquals(Duration.ofMinutes(3), song.getDuration());
    }

    @Test
    void getAddedDate() {
        LocalDateTime addedDate = LocalDateTime.now();
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3),addedDate);
        assertEquals(addedDate, song.getAddedDate());
    }

    @Test
    void setAddedDate() {
        LocalDateTime addedDate = LocalDateTime.now();
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), addedDate);
        song.setAddedDate(addedDate);
        assertEquals(addedDate, song.getAddedDate());
    }

    @Test
    void testToString() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.of(2025, 4, 28, 15, 45, 30));
        Song song2 = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.of(2025, 4, 28, 15, 45, 30));
        assertEquals(song.toString(), song2.toString());
    }
}
