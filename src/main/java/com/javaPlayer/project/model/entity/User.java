package com.javaPlayer.project.model.entity;

import java.util.ArrayList;

public class User {
//    private static int currentUserId;
    private int id;
    private String surname;
    private String name;
    private String password;
    private ArrayList<Playlist> playlistList;

    public User(int id, String surname, String name, String password) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.password = password;
        this.playlistList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
