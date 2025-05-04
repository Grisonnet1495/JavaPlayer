package com.javaPlayer.project.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String pseudo;
    private String password;

    public User(String pseudo, String password) {
        this.id = 0;
        this.pseudo = pseudo;
        this.password = password;
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
    public String toString() {
        return "User{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return pseudo != null && pseudo.equalsIgnoreCase(user.pseudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pseudo, password);
    }
}
