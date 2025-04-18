package com.javaPlayer.project.view;
import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.model.entity.Credentials;
import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.model.entity.Song;

import java.util.ArrayList;

public interface ViewMainWindow {
    Credentials promptForCredentials();

    void updateHomePanel(ArrayList<String> recentPlaylistsTitles, ArrayList<String> allPlaylistsTitles);
    void updatePlaylistPanel(Playlist playlist);
    void updateSearchPanel(ArrayList<Song> songList);

    void showHome();
    void showSearch();
    void showPlaylist();
    void showSettings();
    void showSongDetails();
    void showPlaylistSettings();

    void showMessage(String message);

    void toggleFavoritesForCurrentSong();
    void addCurrentSongToPlaylist();

    void run();
    void stop();

    void setController(MainController c);
}
