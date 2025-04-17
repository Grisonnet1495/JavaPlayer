package com.javaPlayer.project.model.entity;
import java.util.ArrayList;
import java.util.Comparator;

public class Playlist {
    private int id;
    private String title;
    private ArrayList<Song> songList;

    public Playlist(int id, String title, ArrayList<Song> songList) {
        this.id = id;
        this.title = title;
        this.songList = songList;
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

    public Song findSongById(int id) {
        return songList.stream()
                .filter(song -> song.getId() == id)
                .findFirst()
                .orElse(null);
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
}
