package com.javaPlayer.project.model.entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Playlist implements Serializable {
    private String title;
    private LocalDateTime lastViewedDate;
    private ArrayList<Song> songList;

    public Playlist(String title, ArrayList<Song> songList) {
        this.title = title;
        this.lastViewedDate = LocalDateTime.now();
        this.songList = songList;
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

    // Note : Create test
    public void updateLastViewedDate() {
        this.lastViewedDate = LocalDateTime.now();
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }

    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }

    public void addSong(Song song)
    {
        if(songList.stream().noneMatch(list ->
                list.getTitle().equalsIgnoreCase(song.getTitle()) &&
                        list.getArtist().equals(song.getArtist())))
        {
            songList.add(song);
            System.out.println("Chanson ajoutée : " + song.getTitle());
        } else {
            System.out.println("Le son " + song.getTitle() + " est déjà dans la playlist");
        }
    }

    public void removeSong(Song song)
    {
        Song toRemove = songList.stream()
                .filter(s -> s.getTitle().equalsIgnoreCase(song.getTitle()))
                .findFirst()
                .orElse(null);

        if(toRemove != null)
            songList.remove(toRemove);
    }

    public Song findSongByTitle(String title) {
        return songList.stream()
                .filter(song -> song.getTitle().equalsIgnoreCase(title)) //filtre la recherhe
                .findFirst() // dès qu'il trouve
                .orElse(null);
    }

    public void sortSongsByTitle()
    {
        songList.sort(Comparator.comparing(Song::getTitle));
    }

    public void sortSongsByArtist()
    {
        songList.sort(Comparator.comparing(song -> song.getArtist().getPseudo()));
    }

    public void sortSongsByGenre()
    {
        songList.sort(Comparator.comparing(Song::getGenre));
    }

    public void sortSongsByDate()
    {
        songList.sort(Comparator.comparing(Song::getAddedDate));
    }

    @Override
    public String toString() {
        return "Playlist{" +
                ", title='" + title + '\'' +
                ", songList=" + songList +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(title, playlist.title) && Objects.equals(songList, playlist.songList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, songList);
    }
}
