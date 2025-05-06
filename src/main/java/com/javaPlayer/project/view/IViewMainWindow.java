package com.javaPlayer.project.view;
import com.javaPlayer.project.controller.Controller;
import com.javaPlayer.project.model.entity.*;

import java.io.File;
import java.util.ArrayList;

public interface IViewMainWindow {
    // Show authentication dialog
    Credentials promptForCredentials();

    // Show panels
    void showHome();
    void showSearch();
    void showPlaylist();

    // Update UI
    void updateHomePanel(ArrayList<Playlist> recentPlaylistsList, ArrayList<Playlist> allPlaylistsList);
    void updatePlaylistPanel(Playlist playlist);
    void updateSearchPanel(ArrayList<Song> songList);
    void updateSongPanel(String songTitle, String artistPseudo, byte[] songIcon, String duration, String elapsedTime, String remainingTime, boolean isSongFavorite);

    void updateSongActionsPanel(boolean isRandom, boolean isPreviousSongPossible, boolean isLooping, boolean isPlaying);

    String getSelectedPlaylistTitle();

    // Show dialog boxes
    Settings showAndGetSettings(String userPseudo, String userPassword);
    SongDetails showSongDetails(String title, String artist, String playlist, String addedDate, String duration);
    PlaylistSettings showAndGetPlaylistSettings(String playlistTitle, String playlistOwner, boolean canPlaylistBeRenamed, boolean canPlaylistBeDeleted);

    // Show message
    void showMessage(String message);

    // Prompt for actions
    String promptChooseAddToPlaylist(ArrayList<String> playlistTitleList);
    String promptToCreatePlaylist();
    String promptChoosePlaylistToDelete(ArrayList<String> playlistTitleList);

    // Run and stop
    void run();
    void stop();

    // Set controller
    void setController(Controller c);

    // Open and save file
    File openFile(String fileType, String fileExtension);
    String saveFile();
}
