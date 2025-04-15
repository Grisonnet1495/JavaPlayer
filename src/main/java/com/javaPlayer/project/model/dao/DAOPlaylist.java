package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.DataAccessLayer;
import com.javaPlayer.project.model.entity.Playlist;

import java.util.ArrayList;

public class DAOPlaylist implements DataAccessLayer {
    private ArrayList<Playlist> playlists;
    private static int currentId = 1;

    public DAOPlaylist() {
        playlists = new ArrayList<>();
    }

    @Override
    public int addPlaylist(Playlist article) {
        return 0;
    }

    @Override
    public boolean updatePlaylistTitle(String t) {
        return false;
    }

    @Override
    public boolean deletePlaylist(int id) {
        return false;
    }

    @Override
    public boolean deletePlaylist(Playlist p) {
        return false;
    }

    @Override
    public Playlist getPlaylistById(int id) {
        return null;
    }

    @Override
    public String toString() {
        return "unknown";
    }

    @Override
    public ArrayList<Playlist> getPlaylistList() {
        return null;
    }

    // For testing purpose
    public static void main(String[] args) {
        DataAccessLayer dao = new DAOPlaylist();
        try {
            // Test of DAOPlaylist
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
