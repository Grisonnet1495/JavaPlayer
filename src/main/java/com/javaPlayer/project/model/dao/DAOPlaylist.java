package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.FilePathNames;
import com.javaPlayer.project.model.entity.Playlist;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class DAOPlaylist {
    private static int currentId = 1;
    private final String fileName;
    private ArrayList<Playlist> playlistsList;

    public DAOPlaylist() {
        DAOConfig daoConfig = new DAOConfig(FilePathNames.CONFIG);
        if (!daoConfig.isConfigExists("playlistFile")) {
            daoConfig.addConfig("playlistFile", FilePathNames.PLAYLISTS);
        }
        this.fileName = daoConfig.getConfig("playlistFile");
        loadPlaylistFromFile();
    }

    public void savePlaylistInFile()
    {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPlaylistFromFile() {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);

            playlistsList = (ArrayList<Playlist>)ois.readObject();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
