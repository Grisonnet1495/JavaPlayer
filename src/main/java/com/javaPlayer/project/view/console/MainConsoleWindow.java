package com.javaPlayer.project.view.console;

import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.model.entity.Credentials;
import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.model.entity.Song;
import com.javaPlayer.project.view.ViewMainWindow;

import java.util.ArrayList;

public class MainConsoleWindow implements ViewMainWindow {
    @Override
    public Credentials promptForCredentials() {
        return null;
    }

    @Override
    public void updateHomePanel(ArrayList<String> recentPlaylistsTitles, ArrayList<String> allPlaylistsTitles) {

    }

    @Override
    public void updatePlaylistPanel(Playlist playlist) {

    }

    @Override
    public void updateSearchPanel(ArrayList<Song> songList) {

    }

    @Override
    public void showHome() {

    }

    @Override
    public void showSearch() {

    }

    @Override
    public void showPlaylist() {

    }

    @Override
    public void showSettings() {

    }

    @Override
    public void showSongDetails() {

    }

    @Override
    public void showPlaylistSettings() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void toggleFavoritesForCurrentSong() {

    }

    @Override
    public void addCurrentSongToPlaylist() {

    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void setController(MainController c) {

    }
}
