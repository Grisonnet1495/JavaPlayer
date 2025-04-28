package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.model.entity.Song;
import com.javaPlayer.project.model.entity.Artist;
import org.junit.jupiter.api.*;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DAOPlaylistTest {

    private static final String TEST_PSEUDO = "testUser";
    private DAOPlaylist daoPlaylist;

    @BeforeEach
    void setUp() {
        daoPlaylist = new DAOPlaylist(TEST_PSEUDO);
    }

    @AfterEach
    void tearDown() {
        // Clean up the remaining file after each test
        File file = new File(TEST_PSEUDO + "_playlists.properties");

        if (file.exists()) {
            file.delete();
        }
    }

    // Script for creating a test Song list
    private List<Song> createSampleSongs() {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1, "Bohemian Rhapsody", new Artist(1, "Queen"), "Rock", Duration.ofMinutes(3), LocalDateTime.now()));
        songs.add(new Song(2, "Imagine", new Artist(2, "John Lennon"), "Pop", Duration.ofMinutes(2).plusSeconds(52), LocalDateTime.now()));
        return songs;
    }

    @Test
    void testAddAndGetPlaylist() {
        daoPlaylist.getPlaylistsList().clear(); // Reset playlists list

        Playlist playlist = new Playlist("MyPlaylist", new ArrayList<>(createSampleSongs()));
        daoPlaylist.addPlaylist(playlist);

        assertEquals(1, daoPlaylist.getPlaylistsList().size());
        assertEquals("MyPlaylist", daoPlaylist.getPlaylistsList().getFirst().getTitle());
    }


    @Test
    void testSaveAndLoadPlaylists() {
        Playlist playlist = new Playlist("SavedPlaylist", new ArrayList<>(createSampleSongs()));
        daoPlaylist.addPlaylist(playlist);
        daoPlaylist.savePlaylistsToFile();
        daoPlaylist.loadPlaylistsFromFile();

        DAOPlaylist newDaoPlaylist = new DAOPlaylist(TEST_PSEUDO);
        assertNotNull(newDaoPlaylist.getPlaylistsList());
        assertFalse(newDaoPlaylist.getPlaylistsList().isEmpty());
        assertEquals("SavedPlaylist", newDaoPlaylist.getPlaylist(playlist.getTitle()).getTitle());
    }

    @Test
    void testRemovePlaylist() {
        daoPlaylist.getPlaylistsList().clear(); // <-- reset propre au début du test

        Playlist playlist1 = new Playlist("Playlist1", new ArrayList<>(createSampleSongs()));
        Playlist playlist2 = new Playlist("Playlist2", new ArrayList<>(createSampleSongs()));

        daoPlaylist.addPlaylist(playlist1);
        daoPlaylist.addPlaylist(playlist2);

        daoPlaylist.removePlaylist("Playlist1");

        assertEquals(1, daoPlaylist.getPlaylistsList().size());
        assertEquals("Playlist2", daoPlaylist.getPlaylistsList().getFirst().getTitle());
    }

    @Test
    void testGetPlaylist() {
        Playlist playlist = new Playlist("SpecificPlaylist", new ArrayList<>(createSampleSongs()));
        daoPlaylist.addPlaylist(playlist);

        Playlist found = daoPlaylist.getPlaylist("SpecificPlaylist");
        assertNotNull(found);
        assertEquals("SpecificPlaylist", found.getTitle());
    }

    @Test
    void testGetPlaylist_NotFound() {
        Playlist found = daoPlaylist.getPlaylist("NonExistentPlaylist");
        assertNull(found);
    }

    @Test
    void testClearPlaylistsData() {
        Playlist playlist = new Playlist("MyPlaylist", new ArrayList<>(createSampleSongs()));

        daoPlaylist.addPlaylist(playlist);
        daoPlaylist.savePlaylistsToFile();
        daoPlaylist.clearPlaylistsData();
        daoPlaylist.loadPlaylistsFromFile();

        assertTrue(daoPlaylist.getPlaylistsList().isEmpty());
    }
}
