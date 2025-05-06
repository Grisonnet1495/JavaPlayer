package com.javaPlayer.project.model.entity;
import com.javaPlayer.project.model.exception.PlaylistException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Playlist implements Serializable {
    private int id;
    private String title;
    private LocalDateTime lastViewedDate;
    private byte[] icon;
    private ArrayList<Song> songList;

    public Playlist(String title, ArrayList<Song> songList) {
        this.id = 0;
        this.title = title;
        this.lastViewedDate = LocalDateTime.now();

        if (songList == null) {
            this.songList = new ArrayList<>();
        } else {
            this.songList = songList;
        }
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

    // Note : Create test
    public LocalDateTime getLastViewedDate() {
        return lastViewedDate;
    }

    public void setLastViewedDate(LocalDateTime lastViewedDate) {
        this.lastViewedDate = lastViewedDate;
    }

    // Note : Create test
    public void updateLastViewedDate() {
        this.lastViewedDate = LocalDateTime.now();
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }

    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }

    public void addSong(Song song) {
        // Verify if the song already exists in the playlist
        if (songList.stream().anyMatch(list -> list.getId() == song.getId())) {
            throw new PlaylistException("A song with this id already exists !");
        }

//        byte[] songIcon = MusicPlayer.getSongIcon(song.getFilename());
        // Note : Temporary
        byte[] songIcon = null;

        // If it is the first song, set the playlist icon
        if (songList.isEmpty()) {
            icon = songIcon;
        }

        songList.add(song);
    }

    public void removeSong(Song song) {
        // Verify if the song exists in the playlist
        Song songToRemove = songList.stream()
                .filter(s -> s.getId() == song.getId())
                .findFirst()
                .orElse(null);

        if (songToRemove == null) {
            throw new PlaylistException("Song doesn't exist in the playlist !");
        }

        // If it is the first song, set the new playlist icon
        if (songList.get(0).getId() == songToRemove.getId()) {
            if (songList.size() > 1) {
//                icon = MusicPlayer.getSongIcon(songList.get(1).getFilename());
                // Note : Temporary
                icon = null;
            } else {
                icon = null;
            }
        }

        songList.remove(songToRemove);
    }

    public Song findSongById(int id) {
        return songList.stream()
                .filter(song -> song.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Song findSongByTitle(String title) {
        return songList.stream()
                .filter(song -> song.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public void sortSongsById() {
        songList.sort(Comparator.comparingInt(Song::getId));
    }

    public void sortSongsByTitle() {
        songList.sort(Comparator.comparing(Song::getTitle));
    }

    public void sortSongsByArtist() {
        songList.sort(Comparator.comparing(song -> song.getArtist()));
    }

    public void sortSongsByGenre() {
        songList.sort(Comparator.comparing(Song::getGenre));
    }

    public void sortSongsByDuration() {
        songList.sort(Comparator.comparing(Song::getDuration));
    }

    public void sortSongsByAddedDate() {
        songList.sort(Comparator.comparing(Song::getAddedDate));
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", lastViewedDate=" + lastViewedDate +
                ", hasIcon=" + (icon != null && icon.length > 0) +
                ", songList=" + songList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(id, playlist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
