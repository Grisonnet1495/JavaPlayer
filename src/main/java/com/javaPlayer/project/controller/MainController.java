package com.javaPlayer.project.controller;

import com.javaPlayer.project.model.FilePathNames;
import com.javaPlayer.project.model.authentication.Authenticator;
import com.javaPlayer.project.model.authentication.FileAuthenticator;
import com.javaPlayer.project.model.dao.DAOConfig;
import com.javaPlayer.project.model.dao.DAOUser;
import com.javaPlayer.project.model.entity.Credentials;
import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.model.entity.Song;
import com.javaPlayer.project.view.GUI.JFrameMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            view.showSettings();
        } else if (e.getActionCommand().equals(MainControllerActions.SONG_DETAILS)) {
            view.showSongDetails();
        } else if (e.getActionCommand().equals(MainControllerActions.PLAYLIST_SETTINGS)) {
            view.showPlaylistSettings();
        } else if (e.getActionCommand().equals(MainControllerActions.ADD_TO_PLAYLIST)) {
            view.addCurrentSongToPlaylist();
        } else {
            view.showMessage("Button not implemented !");
        }
    }
}
