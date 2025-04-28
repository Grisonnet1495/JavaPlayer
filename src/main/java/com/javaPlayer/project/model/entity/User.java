package com.javaPlayer.project.model.entity;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private String pseudo;
    private String password;
//    private ArrayList<Playlist> playlistList;

    public User() {
        this.pseudo = "Unknown";
        this.password = "";
//        this.playlistList = new ArrayList<>();
    }

    public User(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
//        this.playlistList = new ArrayList<>();
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
//
//    public ArrayList<Playlist> getPlaylistList() {
//        return playlistList;
//    }
//
//    public void setPlaylistList(ArrayList<Playlist> playlistList) {
//        this.playlistList = playlistList;
//    }
//
//    public boolean addPlaylist(Playlist playlist) {
//        if (playlistList.stream().anyMatch(p -> !p.getTitle().equals(playlist.getTitle()))) {
//            playlistList.add(playlist);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean removePlaylistByTitle(String title) {
//        if (playlistList.stream().anyMatch(playlist -> playlist.getTitle().equals(title))) {
//            playlistList.removeIf(playlist -> playlist.getTitle().equals(title));
//            return true;
//        }
//
//        return false;
//    }
//
//    public Playlist findPlaylistByTitle(String title) {
//        return playlistList.stream()
//                .filter(playlist -> playlist.getTitle().equalsIgnoreCase(title))
//                .findFirst()
//                .orElse(null);
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                ", pseudo='" + pseudo + '\'' +
//                ", password='" + password + '\'' +
//                ", playlistList=" + playlistList +
//                '}';
//    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return pseudo != null && pseudo.equalsIgnoreCase(user.pseudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pseudo, password/*, playlistList*/);
    }
}
