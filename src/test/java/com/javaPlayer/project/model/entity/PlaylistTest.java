//package com.javaPlayer.project.model.entity;
//
//import org.junit.jupiter.api.Test;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class PlaylistTest {
//
//    @Test
//    void getTitle() {
//        Playlist playlist = new Playlist("Tron", new ArrayList<>());
//        assertEquals("Tron", playlist.getTitle());
//    }
//
//    @Test
//    void setTitle() {
//        Playlist playlist = new Playlist("Tron", new ArrayList<>());
//        playlist.setTitle("Tron - Daft Punk");
//        assertEquals("Tron - Daft Punk", playlist.getTitle());
//    }
//
//    @Test
//    void getSongList() {
//        ArrayList<Song> songList = new ArrayList<>();
//        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
//        songList.add(song);
//        Playlist playlist = new Playlist("Tron", songList);
//        assertEquals(songList, playlist.getSongList());
//    }
//
//    @Test
//    void setSongList() {
//        ArrayList<Song> newSongs = new ArrayList<>();
//        Song song = new Song(2, "C.L.U", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
//        newSongs.add(song);
//        Playlist playlist = new Playlist("Tron", new ArrayList<>());
//        playlist.setSongList(newSongs);
//        assertEquals(1, playlist.getSongList().size());
//        assertEquals("C.L.U", playlist.getSongList().getFirst().getTitle());
//    }
//
//    @Test
//    void addSong() {
//        Playlist playlist = new Playlist("Tron", new ArrayList<>());
//        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
//        playlist.addSong(song);
//        assertTrue(playlist.getSongList().contains(song));
//    }
//
//    @Test
//    void removeSong() {
//        Song song = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
//        Playlist playlist = new Playlist("Tron", new ArrayList<>(List.of(song)));
//        playlist.removeSong(song);
//        assertFalse(playlist.getSongList().contains(song));
//    }
//
//    @Test
//    void findSongByTitle() {
//        Song song1 = new Song(1, "The Grid", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
//        Song song2 = new Song(2, "C.L.U", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(2).plusSeconds(52), LocalDateTime.now());
//        Playlist playlist = new Playlist("Tron", new ArrayList<>(List.of(song1, song2)));
//
//        Song found = playlist.findSongByTitle("C.L.U");
//        assertNotNull(found);
//        assertEquals("C.L.U", found.getTitle());
//    }
//
//    @Test
//    void sortSongsByTitle() {
//        Song a = new Song(1, "Aerodynamic", new Artist(1, "Daft Punk"), "Electronic", Duration.ofMinutes(3), LocalDateTime.now());
//        Song b = new Song(2, "C.L.U", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(2).plusSeconds(52), LocalDateTime.now());
//        Playlist playlist = new Playlist("Tron", new ArrayList<>(List.of(b, a)));
//
//        playlist.sortSongsByTitle();
//        assertEquals("Aerodynamic", playlist.getSongList().getFirst().getTitle());
//    }
//
//    @Test
//    void sortSongsByArtist() {
//        Song a = new Song(1, "The Grid", new Artist(2, "Zedd"), "Soundtrack", Duration.ofMinutes(3), LocalDateTime.now());
//        Song b = new Song(2, "C.L.U", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(2).plusSeconds(52), LocalDateTime.now());
//        Playlist playlist = new Playlist("Tron", new ArrayList<>(List.of(a, b)));
//
//        playlist.sortSongsByArtist();
//        assertEquals("Daft Punk", playlist.getSongList().getFirst().getArtist().getPseudo());
//    }
//
//    @Test
//    void sortSongsByGenre() {
//        Song a = new Song(1, "Voyager", new Artist(1, "Daft Punk"), "Electronic", Duration.ofMinutes(3), LocalDateTime.now());
//        Song b = new Song(2, "End of Line", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(2).plusSeconds(52), LocalDateTime.now());
//        Playlist playlist = new Playlist("Tron", new ArrayList<>(List.of(b, a)));
//
//        playlist.sortSongsByGenre();
//        assertEquals("Electronic", playlist.getSongList().getFirst().getGenre());
//    }
//
//    @Test
//    void sortSongsByAddedDate() {
//        LocalDateTime olderDate = LocalDateTime.now().minusHours(10);
//        LocalDateTime newerDate = LocalDateTime.now().minusHours(4);
//
//        Song oldSong = new Song(1, "Derezzed", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(3), olderDate);
//        Song newSong = new Song(2, "Fall", new Artist(1, "Daft Punk"), "Soundtrack", Duration.ofMinutes(2).plusSeconds(52), newerDate);
//        Playlist playlist = new Playlist("Tron", new ArrayList<>(List.of(newSong, oldSong)));
//
//        playlist.sortSongsByAddedDate();
//        assertEquals(oldSong, playlist.getSongList().getFirst());
//    }
//}
