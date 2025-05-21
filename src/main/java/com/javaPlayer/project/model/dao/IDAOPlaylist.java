package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.model.entity.Song;

import java.util.*;

public interface IDAOPlaylist {
    // Backup methods
    void loadPlaylistsConfigFile(int userId);
    void savePlaylistsToFile();
    void loadPlaylistsFromFile();

    // Init methods
    void initialisePlaylistsList();

    // For test purpose
    void setPlaylistsList(ArrayList<Playlist> playlistsList);

    // Set and get playlists methods
    ArrayList<Playlist> getPlaylistsList();
    ArrayList<Playlist> getBasePlaylistsList();
    ArrayList<Playlist> getRecentPlaylistsList(int minutes);
    Playlist getPlaylistByName(String playlistName);
    Playlist getPlaylistById(int playlistId);

    // CRUD playlists methods
    void createPlaylist(String playlistTitle);
    void removePlaylist(Playlist playlist);
    void removeAllPlaylists();
    void changePlaylistTitle(String oldTitle, String newTitle);
    void deleteAllCurrentUserData();

    // Playlists check methods
    boolean canPlaylistBeRenamed(String playlistName);
    boolean canPlaylistBeDeleted(String playlistTitle);

    // Get songs methods
    ArrayList<Song> getAllSongs();
    Song getSongById(int songId);
    Playlist getSongPlaylist(Song song);

    // CRUD song methods
    void importSongToPlaylist(Playlist playlist, Song song);
    void deleteSongFromPlaylist(Playlist playlist, Song song);
    void changeSongPlaylist(Song song, Playlist newPlaylist);

    // Song checks methods
    boolean isSongInFavoritesPlaylist(Song song);

    // Export methods
    void exportSong(Song song, String newFilename);

    // Utils methods
    Song getNextSong(Song currentSong, Playlist currentPlaylist );
    Song getPreviousSong(Song currentSong, Playlist currentPlaylist);
    Song getRandomSong(Playlist playlist);
    byte[] loadImageAsBytes(String path);
}
