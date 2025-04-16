package com.javaPlayer.project.model.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {

    @Test
    void getId() {
        Playlist playlist = new Playlist(1, "Tron", new ArrayList<>());
        assertEquals(1, playlist.getId());
    }

    @Test
    void setId() {
        Playlist playlist = new Playlist(1, "Tron", new ArrayList<>());
        playlist.setId(2);
        assertEquals(2, playlist.getId());
    }

    @Test
    void getTitle() {
        Playlist playlist = new Playlist(1, "Tron", new ArrayList<>());
        assertEquals("Tron", playlist.getTitle());
    }

    @Test
    void setTitle() {
        Playlist playlist = new Playlist(1, "Tron", new ArrayList<>());
        playlist.setTitle("Tron - Daft Punk");
        assertEquals("Tron - Daft Punk", playlist.getTitle());
    }

    @Test
    void getSongList() {
        ArrayList<Song> songList = new ArrayList<>();
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        songList.add(song);
        Playlist playlist = new Playlist(1, "Tron", songList);
        assertEquals(songList, playlist.getSongList());
    }

    @Test
    void setSongList() {
        ArrayList<Song> newSongs = new ArrayList<>();
        Song song = new Song(2, "C.L.U", new Artist(1, "Daft Punk"), "Soundtrack", 172, new Date());
        newSongs.add(song);
        Playlist playlist = new Playlist(1, "Tron", new ArrayList<>());
        playlist.setSongList(newSongs);
        assertEquals(1, playlist.getSongList().size());
        assertEquals("C.L.U", playlist.getSongList().get(0).getTitle());
    }

    @Test
    void addSong() {
        Playlist playlist = new Playlist(1, "Tron", new ArrayList<>());
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        playlist.addSong(song);
        assertTrue(playlist.getSongList().contains(song));
    }

    @Test
    void removeSong() {
        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        Playlist playlist = new Playlist(1, "Tron", new ArrayList<>(List.of(song)));
        playlist.removeSong(song);
        assertFalse(playlist.getSongList().contains(song));
    }

    @Test
    void searchSong() {
        Song song1 = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", 180, new Date());
        Song song2 = new Song(2, "C.L.U", new Artist(1, "Daft Punk"), "Soundtrack", 172, new Date());
        Playlist playlist = new Playlist(1, "Tron", new ArrayList<>(List.of(song1, song2)));

        Song found = playlist.searchSong("C.L.U"); // Adapté si la méthode retourne une Song
        assertNotNull(found);
        assertEquals("C.L.U", found.getTitle());
    }

    @Test
    void sortSongByTitle() {
        Song a = new Song(1, "Aerodynamic", new Artist(1, "Daft Punk"), "Electronic", 180, new Date());
        Song b = new Song(2, "C.L.U", new Artist(1, "Daft Punk"), "Soundtrack", 172, new Date());
        Playlist playlist = new Playlist(1, "Tron", new ArrayList<>(List.of(b, a)));

        playlist.sortSongByTitle();
        assertEquals("Aerodynamic", playlist.getSongList().get(0).getTitle());
    }

    @Test
    void sortSongByArtist() {
        Song a = new Song(1, "The Grid", new Artist(2, "Zedd"), "Soundtrack", 180, new Date());
        Song b = new Song(2, "C.L.U", new Artist(1, "Daft Punk"), "Soundtrack", 172, new Date());
        Playlist playlist = new Playlist(1, "Tron", new ArrayList<>(List.of(a, b)));

        playlist.sortSongByArtist();
        assertEquals("Daft Punk", playlist.getSongList().get(0).getArtist().getPseudo());
    }

    @Test
    void sortSongByGenre() {
        Song a = new Song(1, "Voyager", new Artist(1, "Daft Punk"), "Electronic", 200, new Date());
        Song b = new Song(2, "End of Line", new Artist(1, "Daft Punk"), "Soundtrack", 172, new Date());
        Playlist playlist = new Playlist(1, "Tron", new ArrayList<>(List.of(b, a)));

        playlist.sortSongByGenre();
        assertEquals("Electronic", playlist.getSongList().get(0).getGenre());
    }

    @Test
    void sortSongByDate() {
        Date older = new Date(2010); // Ancienne date
        Date newer = new Date(2011); // Plus récente

        Song oldSong = new Song(1, "Derezzed", new Artist(1, "Daft Punk"), "Soundtrack", 180, older);
        Song newSong = new Song(2, "Fall", new Artist(1, "Daft Punk"), "Soundtrack", 180, newer);
        Playlist playlist = new Playlist(1, "Tron", new ArrayList<>(List.of(newSong, oldSong)));

        playlist.sortSongByDate();
        assertEquals(oldSong, playlist.getSongList().get(0));
    }
}
