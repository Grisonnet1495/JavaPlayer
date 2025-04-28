package com.javaPlayer.project.view;
import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.model.entity.*;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public interface ViewMainWindow {
    Credentials promptForCredentials();

    // Show panels
    void showHome();
    void showSearch();
    void showPlaylist();

    // Update UI
    void updateHomePanel(ArrayList<Playlist> recentPlaylistsList, ArrayList<Playlist> allPlaylistsList);
    void updatePlaylistPanel(Playlist playlist);
    void updateSearchPanel(ArrayList<Song> songList);
    void updateSongPanel(String songTitle, String artistPseudo, Icon songIcon, String duration, String elapsedTime, String remainingTime, boolean isSongFavorite);
    String getSelectedPlaylistTitle();
//    void clearSelectedPlaylistTitle();

    // Show dialog boxes
    Settings showAndGetSettings(String userPseudo, String userPassword);
    void showSongDetails(String title, String artist, String playlist, String addedDate, String duration);
    PlaylistSettings showAndGetPlaylistSettings(String playlistTitle, String playlistOwner);

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
    void setController(MainController c);

    // Open file
    File openFile(String fileType, String fileExtension);
}
