package com.javaPlayer.project.view;
import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.model.entity.*;

import java.io.File;
import java.util.ArrayList;

public interface ViewMainWindow {
    Credentials promptForCredentials();

    // Show panels
    void showHome();
    void showSearch();
    void showPlaylist();

    // Update panels
    void updateHomePanel(ArrayList<String> recentPlaylistsTitles, ArrayList<String> allPlaylistsTitles);
    void updatePlaylistPanel(Playlist playlist);
    void updateSearchPanel(ArrayList<Song> songList);

    // Show dialog boxes
    Settings showAndGetSettings(String userPseudo, String userPassword);
    void showSongDetails(String title, String artist, String playlist, String addedDate, String duration);
    PlaylistSettings showAndGetPlaylistSettings();

    // Show message
    void showMessage(String message);

    // Prompt for actions
//    void toggleFavoritesForCurrentSong();
    String promptChooseAddToPlaylist();
    String promptToCreatePlaylist();
    String promptChoosePlaylistToDelete();

    // Run and stop
    void run();
    void stop();

    // Set controller
    void setController(MainController c);

    // Open file
    File openFile(String fileType, String fileExtension);
}
