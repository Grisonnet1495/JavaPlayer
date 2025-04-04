package com.javaPlayer.project.model.entity;
import java.util.ArrayList;

public class Playlist {
    private int id;
    private String titre;
    private ArrayList<Song> songList;

    public Playlist(int id, String titre, ArrayList<Song> songList) {
        this.id = id;
        this.titre = titre;
        this.songList = songList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }

    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }
}
