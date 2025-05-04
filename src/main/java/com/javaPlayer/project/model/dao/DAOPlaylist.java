package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.exception.PlaylistException;
import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.model.entity.Song;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class DAOPlaylist {
    // Playlist config file
    private String playlistsConfigFilename; // Filename of the playlists list config file
    private Properties playlistsConfig; // Current content of th playlist list config file

    // Current user data
    private String currentUserFileName; // Filename of the file containing all user playlists
    private String currentUserDataDirectory; // Directory name of all the user playlists data
    private int currentUserId; // Current user id
    private ArrayList<Playlist> playlistsList; // Current playlists of the user

    public DAOPlaylist(String playlistsConfigFilename) {
//        playlistsConfigFilename = new DAOConfig(DefaultFilePath.CONFIG).getConfig(Constants.USER_PLAYLISTS_CONFIG_KEY);
        this.playlistsConfigFilename = playlistsConfigFilename;

        playlistsConfig = new Properties();

        // Note : To do at the start of the controller
//        loadPlaylistsConfigFile(userId);
//        loadPlaylistsFromFile();
    }

    public void loadPlaylistsConfigFile(int userId) {
        currentUserId = userId;

        // Load the config file
        File playlistsConfigFile = new File(playlistsConfigFilename);

        if (playlistsConfigFile.exists()) {
            try (FileInputStream fis = new FileInputStream(playlistsConfigFile)) {
                playlistsConfig.load(fis);
            } catch (IOException e) {
                throw new PlaylistException("Cannot load playlist config file", e);
            }
        }

        // Create the user config if it doesn't exist
        if (!playlistsConfig.containsKey(userId)) {
            playlistsConfig.setProperty(String.valueOf(userId), "PlaylistsFile_User_" + userId + ".dat");
        }

        // Update the playlists config file
        try (FileOutputStream fos = new FileOutputStream(playlistsConfigFile)) {
            playlistsConfig.store(fos, "User playlists lists configuration file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create the user data directory if it doesn't exist
        currentUserDataDirectory = "Data_User_" + currentUserId;

        File userPlaylistsDirectory = new File(currentUserDataDirectory);

        if (!userPlaylistsDirectory.exists() || !userPlaylistsDirectory.isDirectory()) {
            if (!userPlaylistsDirectory.mkdirs()) {
                throw new PlaylistException("Cannot create user data directory");
            }
        }

        this.currentUserFileName = playlistsConfig.getProperty(String.valueOf(userId));
    }


    public void savePlaylistsToFile() {
        try (FileOutputStream fos = new FileOutputStream(currentUserFileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(playlistsList);
        } catch (IOException e) {
            throw new PlaylistException("Cannot save playlist file", e);
        }
    }

    public void loadPlaylistsFromFile() {
        try (FileInputStream fis = new FileInputStream(currentUserFileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            playlistsList = (ArrayList<Playlist>) ois.readObject();
        } catch (FileNotFoundException e) {
            initialisePlaylistsList();
            savePlaylistsToFile();
        } catch (IOException | ClassNotFoundException e) {
            throw new PlaylistException("Cannot load playlist file", e);
        }
    }

    public void initialisePlaylistsList() {
        playlistsList = new ArrayList<>();
        createPlaylist("Favorites"); // Add a Favorites playlist
        createPlaylist("Unclassed songs"); // Add an Unclassed songs playlist
    }

    // For test purpose
    public void setPlaylistsList(ArrayList<Playlist> playlistsList) {
        this.playlistsList = playlistsList;
    }

    public void createPlaylist(String playlistTitle) {
        // Verify if another playlist has the same title
        if (playlistsList.stream().anyMatch(list -> list.getTitle().equalsIgnoreCase(playlistTitle))) {
            throw new PlaylistException("Playlist already exists !");
        }

        Playlist newPlaylist = new Playlist(playlistTitle, new ArrayList<>());

        // Find an new id
        int id = 1;
        Set<Integer> usedIds = new HashSet<>();

        for (Playlist p : playlistsList) {
            usedIds.add(p.getId());
        }

        while (usedIds.contains(id)) {
            id++;
        }

        // Set the new id and add the playlist
        newPlaylist.setId(id);
        playlistsList.add(newPlaylist);

        // Create a new directory for the playlist
        File playlistDataDirectory = new File(currentUserDataDirectory + File.separator + "Playlist_" + newPlaylist.getId());

        if (!playlistDataDirectory.exists() || !playlistDataDirectory.isDirectory()) {
            if (!playlistDataDirectory.mkdirs()) {
                throw new PlaylistException("Cannot create playlist data directory");
            }
        }
    }

    public void removePlaylist(String playlistName) {
        // Verify if the playlist can be deleted
        if (playlistName.equals("Favorites") || playlistName.equals("Unclassed songs")) {
            throw new PlaylistException("This playlist cannot be removed !");
        }

        // Find playlist to remove
        Playlist playlistToRemove = getPlaylistByName(playlistName);
        if (playlistToRemove == null) {
            throw new PlaylistException("Playlist not found !");
        }

        // Delete the playlist data directory
        File playlistDataDirectory = new File(currentUserDataDirectory + File.separator + "Playlist_" + playlistToRemove.getId());

        if (playlistDataDirectory.exists()) {
            if (!playlistDataDirectory.delete()) {
                throw new PlaylistException("Failed to delete playlist data directory");
            }
        }

        // Delete the playlist from the list
        playlistsList.removeIf(p -> p.getTitle().equals(playlistName));
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

    public ArrayList<String> getRecentPlaylistsTitleList(int minutes) {
        ArrayList<String> recentPlaylistsTitleList = new ArrayList<>();

        LocalDateTime startTime = LocalDateTime.now().minusMinutes(minutes);

        for (Playlist p : playlistsList) {
            if (p.getLastViewedDate().isAfter(startTime)) {
                recentPlaylistsTitleList.add(p.getTitle());
            }
        }

        return recentPlaylistsTitleList;
    }

    public Playlist getPlaylistByName(String playlistName) {
        for (Playlist p : playlistsList) {
            if (p.getTitle().equals(playlistName)) {
                return p;
            }
        }

        return null;
    }

    public Playlist getPlaylistById(int playlistId) {
        for (Playlist p : playlistsList) {
            if (p.getId() == playlistId) {
                return p;
            }
        }

        return null;
    }

    public void clearPlaylistsData() {
        for (Playlist p : playlistsList) {
            try {
                removePlaylist(p.getTitle());
            } catch (PlaylistException e) {
                System.out.println("Cannot delete " + p.getTitle() + "playlist : " + e.getMessage());
            }
        }
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

    public void addSongToPlaylist(Playlist playlist, Song song) {
        // Copy music file to playlist directory
        Path source = Paths.get(song.getFilename());
        Path destination = Paths.get(currentUserDataDirectory + File.separator + "Playlist_" + playlist.getId() + File.separator + "Song_" + song.getId() + song.getSongFileExtension());

        try {
            Files.copy(source, destination);
        } catch (IOException e) {
            throw new PlaylistException("Cannot copy music file to playlist directory", e);
        }

        // Update the song filename
        song.setFilename(destination.toString());

        // Add the song to playlist
        playlist.addSong(song);
    }

    public void removeSongFromPlaylist(Playlist playlist, Song song) {
        // Delete music file from playlist directory
        Path filepath = Paths.get(song.getFilename());

        try {
            Files.delete(filepath);
        } catch (IOException e) {
            throw new PlaylistException("Cannot delete music file from playlist directory", e);
        }

        // Remove the song from the playlist
        playlist.removeSong(song);
    }

    public void moveSongToPlaylist(Song song, Playlist oldPlaylist, Playlist newPlaylist) {
        if (oldPlaylist.equals(newPlaylist)) {
            return;
        }

        addSongToPlaylist(newPlaylist, song);
        removeSongFromPlaylist(oldPlaylist, song);
    }
}
