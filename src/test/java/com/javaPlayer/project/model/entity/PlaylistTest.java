package com.javaPlayer.project.model.entity;

import com.javaPlayer.project.model.exception.PlaylistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {

    private Playlist playlist;
    private Song song1;
    private Song song2;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("test_song", ".mp3");
        tempFile.deleteOnExit();

        song1 = new Song("Song A", "Artist A", "Rock", Duration.ofMinutes(3), LocalDateTime.now().minusDays(1), tempFile.getAbsolutePath());
        song1.setId(1);

        song2 = new Song("Song B", "Artist B", "Pop", Duration.ofMinutes(2), LocalDateTime.now(), tempFile.getAbsolutePath());
        song2.setId(2);

        ArrayList<Song> songs = new ArrayList<>();
        songs.add(song1);
        songs.add(song2);

        playlist = new Playlist("My Playlist", songs);
    }

    @Test
    void getId() {
        assertEquals(0, playlist.getId());
    }

    @Test
    void setId() {
        playlist.setId(10);
        assertEquals(10, playlist.getId());
    }

    @Test
    void getTitle() {
        assertEquals("My Playlist", playlist.getTitle());
    }

    @Test
    void setTitle() {
        playlist.setTitle("New Playlist");
        assertEquals("New Playlist", playlist.getTitle());
    }

    @Test
    void getLastViewedDate() {
        assertNotNull(playlist.getLastViewedDate());
    }

    @Test
    void setLastViewedDate() {
        LocalDateTime now = LocalDateTime.now();
        playlist.setLastViewedDate(now);
        assertEquals(now, playlist.getLastViewedDate());
    }

    @Test
    void getIcon() {
        assertNull(playlist.getIcon());
    }

    @Test
    void setIcon() {
        byte[] icon = {1, 2, 3};
        playlist.setIcon(icon);
        assertArrayEquals(icon, playlist.getIcon());
    }

    @Test
    void getSongList() {
        assertEquals(2, playlist.getSongList().size());
    }

    @Test
    void setSongList() {
        ArrayList<Song> newList = new ArrayList<>();
        newList.add(song1);
        playlist.setSongList(newList);
        assertEquals(1, playlist.getSongList().size());
    }

    @Test
    void addSong() {
        Song newSong = new Song("Song C", "Artist C", "Jazz", Duration.ofMinutes(4), LocalDateTime.now(), tempFile.getAbsolutePath());
        newSong.setId(3);
        playlist.addSong(newSong);
        assertTrue(playlist.getSongList().contains(newSong));
    }

    @Test
    void addSong_throwsExceptionIfDuplicateId() {
        Song duplicate = new Song("Duplicate Song", "Another Artist", "Genre", Duration.ofMinutes(2), LocalDateTime.now(), tempFile.getAbsolutePath());
        duplicate.setId(1); // same ID as song1
        assertThrows(PlaylistException.class, () -> playlist.addSong(duplicate));
    }

    @Test
    void removeSong() {
        playlist.removeSong(song1);
        assertFalse(playlist.getSongList().contains(song1));
    }

    @Test
    void findSongById() {
        assertEquals(song2, playlist.findSongById(2));
    }

    @Test
    void findSongByTitle() {
        assertEquals(song1, playlist.findSongByTitle("Song A"));
    }

    @Test
    void sortSongsById() {
        playlist.sortSongsById();
        assertEquals(1, playlist.getSongList().get(0).getId());
    }

    @Test
    void sortSongsByTitle() {
        playlist.sortSongsByTitle();
        assertEquals("Song A", playlist.getSongList().get(0).getTitle());
    }

    @Test
    void sortSongsByArtist() {
        playlist.sortSongsByArtist();
        assertEquals("Artist A", playlist.getSongList().get(0).getArtist());
    }

    @Test
    void sortSongsByGenre() {
        playlist.sortSongsByGenre();
        assertEquals("Pop", playlist.getSongList().get(0).getGenre());
        assertEquals("Rock", playlist.getSongList().get(1).getGenre());
    }

    @Test
    void sortSongsByDuration() {
        playlist.sortSongsByDuration();
        assertEquals(2, playlist.getSongList().get(0).getDuration().toMinutes());
    }

    @Test
    void sortSongsByAddedDate() {
        playlist.sortSongsByAddedDate();
        assertEquals(song1, playlist.getSongList().get(0));
    }

    @Test
    void testToString() {
        String result = playlist.toString();
        assertTrue(result.contains("My Playlist"));
        assertTrue(result.contains("songList"));
    }

    @Test
    void testEquals() {
        Playlist same = new Playlist("Copy", new ArrayList<>());
        same.setId(playlist.getId());
        assertEquals(playlist, same);
    }

    @Test
    void testHashCode() {
        Playlist same = new Playlist("Copy", new ArrayList<>());
        same.setId(playlist.getId());
        assertEquals(playlist.hashCode(), same.hashCode());
    }
}
