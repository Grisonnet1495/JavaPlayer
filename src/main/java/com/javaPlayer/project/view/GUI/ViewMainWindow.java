package com.javaPlayer.project.view.GUI;
import com.javaPlayer.project.controller.MainController;

public interface ViewMainWindow {
    void showHomePanel();
    void showSearchPanel();
    void showPlaylist(int playlistId);
    void showSettings();
    void showSongDetails();
    void showPlaylistSettings();
    void toggleFavoritesForCurrentSong();
    void addCurrentSongToPlaylist();
    void showMessage(String message);

    void run();
    void setController(MainController c);
}
