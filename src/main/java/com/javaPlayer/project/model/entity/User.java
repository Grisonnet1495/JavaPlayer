package com.javaPlayer.project.model.entity;

import java.util.ArrayList;

public class User {
//    private static int currentUserId;
    private int id;
    private String pseudo;
    private String password;
    private ArrayList<Playlist> playlistList;

    public User(int id) {
        this.pseudo = "Unknown";
        this.password = "";
        this.playlistList = new ArrayList<>();
    }

    public User(int id, String pseudo, String password) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.playlistList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String surname) {
        this.pseudo = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Playlist> getPlaylistList() {
        return playlistList;
    }

    public void setPlaylistList(ArrayList<Playlist> playlistList) {
        this.playlistList = playlistList;
    }

    public boolean addPlaylist(Playlist playlist) {
        if (playlistList.stream().anyMatch(p -> p.getId() != playlist.getId())) {
            playlistList.add(playlist);
            return true;
        }

        return false;
    }

    public boolean removePlaylistById(int id) {
        if (playlistList.stream().anyMatch(playlist -> playlist.getId() == id)) {
            playlistList.removeIf(playlist -> playlist.getId() == id);
            return true;
        }

        return false;
    }

    public Playlist findPlaylistById(int id) {
        return playlistList.stream()
                .filter(playlist -> playlist.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Playlist findPlaylistByTitle(String title) {
        return playlistList.stream()
                .filter(playlist -> playlist.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                ", playlistList=" + playlistList +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return pseudo != null && pseudo.equalsIgnoreCase(user.pseudo);
    }
}
