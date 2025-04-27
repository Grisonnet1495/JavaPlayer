package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.FilePathNames;
import com.javaPlayer.project.model.entity.Playlist;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class DAOPlaylist {
    private String fileName;
    private ArrayList<Playlist> playlistsList;

    public DAOPlaylist(String pseudo) {
        makeConfigForPlaylist(pseudo);
        loadPlaylistsFromFile(pseudo);
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

    public void loadPlaylistsFromFile(String pseudo) {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            playlistsList = (ArrayList<Playlist>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            playlistsList = new ArrayList<>();
        }
    }

    public void removePlaylist(String playlistName) {
        playlistsList.removeIf(p -> p.getTitle().equals(playlistName));
    }

    public ArrayList<Playlist> getPlaylistsList() {
        return playlistsList;
    }

    public Playlist getPlaylist(String playlistName) {
        for (Playlist p : playlistsList) {
            if (p.getTitle().equals(playlistName)) {
                return p;
            }
        }
        return null;
    }

    public void makeConfigForPlaylist(String pseudo) {
        DAOConfig daoConfig = new DAOConfig(FilePathNames.CONFIG);
        if (!daoConfig.isConfigExists("playlistFile")) {
            daoConfig.addConfig("playlistFile", FilePathNames.PLAYLISTS);
        }

        String playlistFilePath = daoConfig.getConfig("playlistFile");

        Properties prop = new Properties();
        File file = new File(playlistFilePath);

        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                prop.load(fis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!prop.containsKey(pseudo)) {
            prop.setProperty(pseudo, pseudo + "_playlist" + ".dat");
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            prop.store(fos, "fichier playlist des utilisateurs");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.fileName = prop.getProperty(pseudo);
    }
}

