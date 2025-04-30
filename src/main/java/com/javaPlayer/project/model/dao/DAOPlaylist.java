package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.utils.DefaultFilePath;
import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.model.entity.Song;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;

public class DAOPlaylist {
    private String fileName;
    private ArrayList<Playlist> playlistsList;

    private Properties configProps;
    private String configFilePath;
    private File configFile;

    public DAOPlaylist(String pseudo) {
        this.configFilePath = new DAOConfig(DefaultFilePath.CONFIG).getConfig("playlistsFile");
        this.configFile = new File(configFilePath);
        this.configProps = new Properties();

        retrievePlaylistFile(pseudo);
        loadPlaylistsFromFile();
    }

    private void retrievePlaylistFile(String pseudo) {
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                configProps.load(fis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!configProps.containsKey(pseudo)) {
            configProps.setProperty(pseudo, pseudo + "_playlists.properties");
        }

        try (FileOutputStream fos = new FileOutputStream(configFile)) {
            configProps.store(fos, "User playlists configuration file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.fileName = configProps.getProperty(pseudo);
    }


    public void savePlaylistsToFile() {
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
        } catch (FileNotFoundException e) {
            initialisePlaylistsList();
            savePlaylistsToFile();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialisePlaylistsList() {
        playlistsList = new ArrayList<>();
        addPlaylist(new Playlist("Favorites", new ArrayList<>())); // Add an All playlist
        addPlaylist(new Playlist("Unclassed songs", new ArrayList<>())); // Add a Favorites playlist
    }

    public void changeUserPseudo(String oldPseudo, String newPseudo) {
        if (configFile.exists()) {
            if (configProps.containsKey(oldPseudo)) {
                this.fileName = configProps.getProperty(oldPseudo);
                String newFileName = newPseudo + "_playlists.properties";
                loadPlaylistsFromFile();
                configProps.remove(oldPseudo);
                configProps.setProperty(newPseudo, newFileName);
                File file = new File(fileName);
                if(file.exists()){
                    file.delete();
                }
                this.fileName = newFileName;
                savePlaylistsToFile();
            }
        }
    }

    public void addPlaylist(Playlist p) {
        playlistsList.add(p);
    }

    public void removePlaylist(String playlistName) {
        playlistsList.removeIf(p -> p.getTitle().equals(playlistName));
    }

    // For test purpose
    public void setPlaylistsList(ArrayList<Playlist> playlistsList) {
        this.playlistsList = playlistsList;
    }

    public ArrayList<Playlist> getPlaylistsList() {
        return playlistsList;
    }

    public ArrayList<String> getPlaylistsTitleList() {
        ArrayList<String> playlistsTitleList = new ArrayList<>();

        for (Playlist p : playlistsList) {
            playlistsTitleList.add(p.getTitle());
        }
        return playlistsTitleList;
    }

    public ArrayList<Playlist> getRecentPlaylistsList(int minutes) {
        ArrayList<Playlist> recentPlaylistsList = new ArrayList<>();

        LocalDateTime startTime = LocalDateTime.now().minusMinutes(minutes);

        for (Playlist p : playlistsList) {
            if (p.getLastViewedDate().isAfter(startTime)) {
                recentPlaylistsList.add(p);
            }
        }

        return recentPlaylistsList;
    }

//    public ArrayList<String> getRecentPlaylistsTitleList(int minutes) {
//        ArrayList<String> recentPlaylistsTitleList = new ArrayList<>();
//
//        LocalDateTime startTime = LocalDateTime.now().minusMinutes(minutes);
//
//        for (Playlist p : playlistsList) {
//            if (p.getLastViewedDate().isAfter(startTime)) {
//                recentPlaylistsTitleList.add(p.getTitle());
//            }
//        }
//
//        return recentPlaylistsTitleList;
//    }

    public Playlist getPlaylist(String playlistName) {
        for (Playlist p : playlistsList) {
            if (p.getTitle().equals(playlistName)) {
                return p;
            }
        }
        return null;
    }

    public void clearPlaylistsData() {
        playlistsList.clear();
        savePlaylistsToFile();
    }

    public Playlist getSongPlaylist(Song song) {
        for (Playlist p : playlistsList) {
            for (Song s : p.getSongList()) {
                if (s.equals(song)) {
                    return p;
                }
            }
        }

        return null;
    }
}
