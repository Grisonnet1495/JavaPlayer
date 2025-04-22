package com.javaPlayer.project.controller;

import com.javaPlayer.project.model.authentication.Authenticator;
import com.javaPlayer.project.model.dao.DAOConfig;
import com.javaPlayer.project.model.dao.DAOUser;
import com.javaPlayer.project.model.entity.*;
import com.javaPlayer.project.view.GUI.JFrameMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public final class MainController implements ActionListener {
    private DAOConfig daoConfig;
    private DAOUser daoUser;
    private JFrameMainWindow view;
    private Authenticator authenticator;
    private String userPseudo;


    public MainController(JFrameMainWindow view, DAOConfig daoConfig, Authenticator authenticator) {
        this.daoConfig = daoConfig;
        this.view = view;
        this.view.setController(this);
        this.authenticator = authenticator;

        userPseudo = authenticate();
        switchUser(userPseudo);
        view.setVisible(true);

        // Note : Temporary
        ArrayList<String> recentPlaylistsTitleList = new ArrayList<>();
        ArrayList<String> allPlaylistsTitleList = new ArrayList<>();
        recentPlaylistsTitleList.add("Playlist 1");
        allPlaylistsTitleList.add("Playlist 1");
        allPlaylistsTitleList.add("Playlist 2");
        allPlaylistsTitleList.add("Playlist 3");

        view.updateHomePanel(recentPlaylistsTitleList, allPlaylistsTitleList);
        view.showHome();
    }

    public String authenticate() {
        Credentials credentials;
        boolean isAuthenticated = false;

        do {
            // Show the account chooser dialog
            credentials = view.promptForCredentials();

            // Clicked on 'Cancel' or closed the dialog
            if (credentials.isCancellingRequest()) {
                System.exit(0);
            }

            // Create an account or log in to an existing account
            if (credentials.isCreatingAccount()) {
                if (authenticator.isLoginExists(credentials.getUsername())) {
                    view.showMessage("This pseudo already exists");
                } else {
                    authenticator.addUsers(credentials.getUsername(), credentials.getPassword());
                    isAuthenticated = true;
                }
            } else {
                isAuthenticated = authenticator.authenticate(credentials.getUsername(), credentials.getPassword());

                if (!isAuthenticated) {
                    view.showMessage("Password incorrect");
                }
            }
        } while (!isAuthenticated);

        return credentials.getUsername();
    }

    public void switchUser(String userPseudo) {
        daoUser = new DAOUser(userPseudo);
    }

    public void run() {
        view.run();
    }

    public void stop() {
        view.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(MainControllerActions.HOME)) {
            // Note : Temporary
            ArrayList<String> recentPlaylistsTitleList = new ArrayList<>();
            ArrayList<String> allPlaylistsTitleList = new ArrayList<>();
            recentPlaylistsTitleList.add("Playlist 1");
            recentPlaylistsTitleList.add("Playlist 2");
            recentPlaylistsTitleList.add("Playlist 3");
            recentPlaylistsTitleList.add("Playlist 4");
            allPlaylistsTitleList.add("Playlist 1");
            allPlaylistsTitleList.add("Playlist 2");
            allPlaylistsTitleList.add("Playlist 3");
            allPlaylistsTitleList.add("Playlist 4");
            allPlaylistsTitleList.add("Playlist 5");
            allPlaylistsTitleList.add("Playlist 6");
            allPlaylistsTitleList.add("Playlist 7");

            view.updateHomePanel(recentPlaylistsTitleList, allPlaylistsTitleList);
            view.showHome();
        } else if (e.getActionCommand().equals(MainControllerActions.SEARCH)) {
            // Note : Temporary
            view.updateSearchPanel(new ArrayList<Song>());
            view.showSearch();
        } else if (e.getActionCommand().equals(MainControllerActions.FAVORITES)) {
            // Note : Temporary
            view.updatePlaylistPanel(new Playlist(0, "Playlist 1", new ArrayList<Song>()));
            view.showPlaylist();
        } else if (e.getActionCommand().equals(MainControllerActions.SWITCH_ACCOUNT)) {
            view.stop();
            userPseudo = authenticate();
            switchUser(userPseudo);
            view.run();

            // Note : Temporary
            ArrayList<String> recentPlaylistsTitleList = new ArrayList<>();
            ArrayList<String> allPlaylistsTitleList = new ArrayList<>();
            recentPlaylistsTitleList.add("Playlist A");
            recentPlaylistsTitleList.add("Playlist B");
            recentPlaylistsTitleList.add("Playlist C");
            recentPlaylistsTitleList.add("Playlist D");
            allPlaylistsTitleList.add("Playlist A");
            allPlaylistsTitleList.add("Playlist B");
            allPlaylistsTitleList.add("Playlist C");
            allPlaylistsTitleList.add("Playlist D");
            allPlaylistsTitleList.add("Playlist E");
            allPlaylistsTitleList.add("Playlist F");
            allPlaylistsTitleList.add("Playlist G");

            view.updateHomePanel(recentPlaylistsTitleList, allPlaylistsTitleList);
            view.showHome();
        } else if (e.getActionCommand().equals(MainControllerActions.SETTINGS)) {
            // Note : Retrieve user pseudo and password
            Settings settings = view.showAndGetSettings(null, null);

            if (settings != null) {
                // Update user settings

                // Note : Temporary
                System.out.println("Settings updated : {" + settings.getUserPseudo() + ", " + settings.getUserPassword() + ", " + settings.isDeletingAllData() + "}");
            }
        } else if (e.getActionCommand().equals(MainControllerActions.SONG_DETAILS)) {
            view.showSongDetails(null, null, null, null, null);
        } else if (e.getActionCommand().equals(MainControllerActions.PLAYLIST_SETTINGS)) {
            PlaylistSettings playlistSettings = view.showAndGetPlaylistSettings();

            if (playlistSettings != null) {
                // Update playlist

                // Note : Temporary
                System.out.println("Playlist updated : {" + playlistSettings.getPlaylistName() + ", " + playlistSettings.isDeletingPlaylist() + "}");
            }
        } else if (e.getActionCommand().equals(MainControllerActions.PAUSE_PLAY)) {
            // Pause or play song
        } else if (e.getActionCommand().equals(MainControllerActions.PREVIOUS)) {
            // Play previous song
        } else if (e.getActionCommand().equals(MainControllerActions.NEXT)) {
            // Play next song
        } else if (e.getActionCommand().equals(MainControllerActions.RANDOM)) {
            // Count the number of songs in the actual playlist
            // Choose a random number between 1 and the number of songs
            // Play the song at this position in the playlist
        } else if (e.getActionCommand().equals(MainControllerActions.LOOP)) {
            // Change the loop flag to true
        } else if (e.getActionCommand().equals(MainControllerActions.ADD_TO_FAVORITES)) {
            // Add song to the Favorites playlist
            // Update the UI
        } else if (e.getActionCommand().equals(MainControllerActions.ADD_TO_PLAYLIST)) {
            String selectedPlaylist = view.promptChooseAddToPlaylist();

            if (selectedPlaylist != null) {
                // Add current song to selected playlist
                // Update the UI

                // Note : Temporary
                System.out.println("Song added to playlist : " + selectedPlaylist);
            }
        } else if (e.getActionCommand().equals(MainControllerActions.OPEN_SONG)) {
            File songFile = view.openFile("Audio file (*.mp3)", "mp3");

            if (songFile != null) {
                // Add song to the All song playlist

                // Note : Temporary
                System.out.println("Song opened : " + songFile.getAbsolutePath());
            }
        } else if (e.getActionCommand().equals(MainControllerActions.CREATE_BACKUP)) {
            // Export all app data
        } else if (e.getActionCommand().equals(MainControllerActions.REMOVE_SONG_FROM_FAVORITES)) {
            // Remove song from the Favorites playlist
            // Update the UI
        } else if (e.getActionCommand().equals(MainControllerActions.REMOVE_SONG_FROM_PLAYLIST)) {
            // Remove song from the current playlist
            // Add song to the All song playlist

            view.showMessage("Song removed from playlist and placed in All song playlist");

            // Update the UI
        } else if (e.getActionCommand().equals(MainControllerActions.CREATE_PLAYLIST)) {
            // Ask for the new playlist name
            String playlistName = view.promptToCreatePlaylist();

            if (playlistName != null) {
                // Create a new playlist
                // Update the UI

                // Note : Temporary
                System.out.println("Playlist created : " + playlistName);
            }
        } else if (e.getActionCommand().equals(MainControllerActions.DELETE_PLAYLIST)) {
            String playlistToDelete = view.promptChoosePlaylistToDelete();

            if (playlistToDelete != null) {
                // Delete playlist
                // Update the UI

                // Note : Temporary
                System.out.println("Playlist deleted : " + playlistToDelete);
            }
        } else if (e.getActionCommand().equals(MainControllerActions.EXPORT_PLAYLIST)) {
            // Export the current playlist
        } else if (e.getActionCommand().equals(MainControllerActions.IMPORT_PLAYLIST)) {
            // Import a playlist
        } else {
            view.showMessage("Button not implemented !");
        }
    }
}
