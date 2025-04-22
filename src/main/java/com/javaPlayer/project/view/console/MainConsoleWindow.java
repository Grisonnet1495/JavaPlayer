package com.javaPlayer.project.view.console;

import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.model.entity.*;
import com.javaPlayer.project.view.ViewMainWindow;

import java.io.File;
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
    public Settings showAndGetSettings(String userPseudo, String userPassword) {

        return null;
    }

    @Override
    public void showSongDetails(String title, String artist, String playlist, String addedDate, String duration) {

    }

    @Override
    public PlaylistSettings showAndGetPlaylistSettings() {

        return null;
    }

    @Override
    public void showMessage(String message) {

    }

//    @Override
//    public void toggleFavoritesForCurrentSong() {
//
//    }

    @Override
    public String promptChooseAddToPlaylist() {

        return null;
    }

    @Override
    public String promptToCreatePlaylist() {
        return null;
    }

    @Override
    public String promptChoosePlaylistToDelete() {
        return "";
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

    @Override
    public File openFile(String fileType, String fileExtension) {
        return null;
    }
}
