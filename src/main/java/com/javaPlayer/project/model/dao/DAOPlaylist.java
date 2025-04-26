package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.FilePathNames;
import com.javaPlayer.project.model.entity.Playlist;

import java.io.*;
import java.util.ArrayList;

public class DAOPlaylist {
    private final String fileName;
    private ArrayList<Playlist> playlistsList;

    public DAOPlaylist(String pseudo) {

        loadPlaylistsFromFile();
    }

    public void addPlaylist(Playlist p) {
        playlistsList.add(p);
    }

    public void savePlaylistsToFile(Playlist newPlaylist) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(playlistsList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPlaylistsFromFile() {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            playlistsList = (ArrayList<Playlist>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            playlistsList = new ArrayList<>();
        }
    }

    public void removePlaylist(String playlistName) {

    }

    public ArrayList<Playlist> getPlaylistsList() {
        return playlistsList;
    }

    public Playlist getPlaylist(String playlistName) {
    }

    public void makeConfigForPlaylist(){
        DAOConfig daoConfig = new DAOConfig(FilePathNames.CONFIG);
        if (!daoConfig.isConfigExists("playlistFile")) {
            daoConfig.addConfig("playlistFile", FilePathNames.PLAYLISTS);
        }




    }
}
