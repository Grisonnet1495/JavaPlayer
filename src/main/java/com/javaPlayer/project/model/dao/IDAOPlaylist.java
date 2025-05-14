package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.model.entity.Song;
import com.javaPlayer.project.model.exception.PlaylistException;
import com.javaPlayer.project.utils.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

public interface IDAOPlaylist {
    // Backup methods
    public void loadPlaylistsConfigFile(int userId);
    public void savePlaylistsToFile();
    public void loadPlaylistsFromFile();

    // Init methods
    public void initialisePlaylistsList();

    // For test purpose
    public void setPlaylistsList(ArrayList<Playlist> playlistsList);

    // Set and get playlists methods
    public ArrayList<Playlist> getPlaylistsList();
    public ArrayList<Playlist> getBasePlaylistsList();
    public ArrayList<String> getPlaylistsTitleList();
    public ArrayList<Playlist> getRecentPlaylistsList(int minutes);
    public ArrayList<String> getRecentPlaylistsTitleList(int minutes);
    public Playlist getPlaylistByName(String playlistName);
    public Playlist getPlaylistById(int playlistId);

    // CRUD playlists methods
    public void createPlaylist(String playlistTitle);
    public void removePlaylist(Playlist playlist);
    public void removeAllPlaylists();
    public void changePlaylistTitle(String oldTitle, String newTitle);
    public void deleteAllCurrentUserData();

    // Playlists check methods
    public boolean canPlaylistBeRenamed(String playlistName);
    public boolean canPlaylistBeDeleted(String playlistTitle);

    // Get songs methods
    public ArrayList<Song> getAllSongs();
    public Song getSongById(int songId);
    public Playlist getSongPlaylist(Song song);

    // CRUD song methods
    public void importSongToPlaylist(Playlist playlist, Song song);
    public void deleteSongFromPlaylist(Playlist playlist, Song song);
    public void changeSongPlaylist(Song song, Playlist newPlaylist);

    // Song checks methods
    public boolean isSongInFavoritesPlaylist(Song song);

    // Export methods
    public void exportSong(Song song, String newFilename);

    // Utils methods
    public Song getFirstSong();
    public Song getNextSong(Song currentSong, Playlist currentPlaylist );
    public Song getPreviousSong(Song currentSong, Playlist currentPlaylist);
    public Song getRandomSong(Playlist playlist);
    public byte[] loadImageAsBytes(String path);
}
