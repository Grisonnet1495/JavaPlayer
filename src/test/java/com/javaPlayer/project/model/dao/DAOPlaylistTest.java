package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.model.entity.Song;
import com.javaPlayer.project.model.entity.Artist;
import org.junit.jupiter.api.*;
import java.io.File;
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
        // Nettoyer le fichier généré après chaque test
        File file = new File("playlists.properties");
        if (file.exists()) {
            file.delete();
        }
    }

    // Petit utilitaire pour créer une liste de Song de test
    private List<Song> createSampleSongs() {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(1, "Bohemian Rhapsody", new Artist(1, "Queen"), "Rock", 354, new Date()));
        songs.add(new Song(2, "Imagine", new Artist(2, "John Lennon"), "Pop", 183, new Date()));
        return songs;
    }

    @Test
    void testAddAndGetPlaylist() {
        daoPlaylist.getPlaylistsList().clear(); // <-- reset propre au début du test

        Playlist playlist = new Playlist(1, "MyPlaylist", new ArrayList<>(createSampleSongs()));
        daoPlaylist.addPlaylist(playlist);

        assertEquals(1, daoPlaylist.getPlaylistsList().size());
        assertEquals("MyPlaylist", daoPlaylist.getPlaylistsList().get(0).getTitle());
    }


    @Test
    void testSaveAndLoadPlaylists() {
        Playlist playlist = new Playlist(2, "SavedPlaylist", new ArrayList<>(createSampleSongs()));
        daoPlaylist.addPlaylist(playlist);
        daoPlaylist.savePlaylistsToFile(playlist);

        DAOPlaylist newDaoPlaylist = new DAOPlaylist(TEST_PSEUDO);
        assertNotNull(newDaoPlaylist.getPlaylistsList());
        assertFalse(newDaoPlaylist.getPlaylistsList().isEmpty());
        assertEquals("SavedPlaylist", newDaoPlaylist.getPlaylistsList().get(0).getTitle());
    }

    @Test
    void testRemovePlaylist() {
        daoPlaylist.getPlaylistsList().clear(); // <-- reset propre au début du test

        Playlist playlist1 = new Playlist(3, "Playlist1", new ArrayList<>(createSampleSongs()));
        Playlist playlist2 = new Playlist(4, "Playlist2", new ArrayList<>(createSampleSongs()));

        daoPlaylist.addPlaylist(playlist1);
        daoPlaylist.addPlaylist(playlist2);

        daoPlaylist.removePlaylist("Playlist1");

        assertEquals(1, daoPlaylist.getPlaylistsList().size());
        assertEquals("Playlist2", daoPlaylist.getPlaylistsList().get(0).getTitle());
    }

    @Test
    void testGetPlaylist() {
        Playlist playlist = new Playlist(5, "SpecificPlaylist", new ArrayList<>(createSampleSongs()));
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
}
