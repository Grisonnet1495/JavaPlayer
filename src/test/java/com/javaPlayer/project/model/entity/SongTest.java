package com.javaPlayer.project.model.entity;

import com.javaPlayer.project.model.exception.SongException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {

    private Song song;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("test-song", ".mp3");
        tempFile.deleteOnExit();

        song = new Song("Title", "Artist", "Genre", Duration.ofMinutes(3), LocalDateTime.now(), tempFile.getAbsolutePath());
    }

    @Test
    void getId() {
        song.setId(42);
        assertEquals(42, song.getId());
    }

    @Test
    void setId() {
        song.setId(5);
        assertEquals(5, song.getId());
    }

    @Test
    void getTitle() {
        assertEquals("Title", song.getTitle());
    }

    @Test
    void setTitle() {
        song.setTitle("New Title");
        assertEquals("New Title", song.getTitle());
    }

    @Test
    void getArtist() {
        assertEquals("Artist", song.getArtist());
    }

    @Test
    void setArtist() {
        song.setArtist("New Artist");
        assertEquals("New Artist", song.getArtist());
    }

    @Test
    void getGenre() {
        assertEquals("Genre", song.getGenre());
    }

    @Test
    void setGenre() {
        song.setGenre("New Genre");
        assertEquals("New Genre", song.getGenre());
    }

    @Test
    void getDuration() {
        assertEquals(Duration.ofMinutes(3), song.getDuration());
    }

    @Test
    void getFormattedDuration() {
        song.setDuration(Duration.ofSeconds(95)); // 1:35
        assertEquals("01:35", song.getFormattedDuration());

        song.setDuration(Duration.ofHours(1).plusMinutes(2).plusSeconds(3));
        assertEquals("1:02:03", song.getFormattedDuration());
    }

    @Test
    void setDuration() {
        song.setDuration(Duration.ofMinutes(4));
        assertEquals(Duration.ofMinutes(4), song.getDuration());
    }

    @Test
    void getAddedDate() {
        assertNotNull(song.getAddedDate());
    }

    @Test
    void setAddedDate() {
        LocalDateTime now = LocalDateTime.now();
        song.setAddedDate(now);
        assertEquals(now, song.getAddedDate());
    }

    @Test
    void getFilename() {
        assertEquals(tempFile.getAbsolutePath(), song.getFilename());
    }

    @Test
    void setFilename_validFile() {
        assertDoesNotThrow(() -> song.setFilename(tempFile.getAbsolutePath()));
    }

    @Test
    void setFilename_empty_shouldThrowException() {
        SongException exception = assertThrows(SongException.class, () -> song.setFilename(""));
        assertEquals("Filename can't be empty !", exception.getMessage());
    }

    @Test
    void setFilename_nonExisting_shouldThrowException() {
        SongException exception = assertThrows(SongException.class, () -> song.setFilename("nonexistent.mp3"));
        assertEquals("File doesn't exist !", exception.getMessage());
    }

    @Test
    void getSongFileExtension() {
        assertEquals("mp3", song.getSongFileExtension());
    }

    @Test
    void testToString() {
        String result = song.toString();
        assertTrue(result.contains("title='Title'"));
        assertTrue(result.contains("artist=Artist"));
        assertTrue(result.contains("filename='" + tempFile.getAbsolutePath() + "'"));
    }

    @Test
    void testEquals() {
        Song other = new Song(song);
        other.setId(song.getId());
        assertEquals(song, other);
    }

    @Test
    void testHashCode() {
        song.setId(10);
        assertEquals(Integer.hashCode(10), song.hashCode());
    }
}
