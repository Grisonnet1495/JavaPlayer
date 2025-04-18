package com.javaPlayer.project.controller;

import com.javaPlayer.project.model.authentication.Authenticator;
import com.javaPlayer.project.model.dao.DAOUser;
import com.javaPlayer.project.view.GUI.JDialogAccountChooser;
import com.javaPlayer.project.view.GUI.JFrameMainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class MainController implements ActionListener {
    private DAOUser daoUser;
    private JFrameMainWindow view;
    private Authenticator authenticator;
    private String userPseudo;


    public MainController(JFrameMainWindow view, Authenticator authenticator) {
        this.view = view;
        this.view.setController(this);
        this.authenticator = authenticator;

        userPseudo = authenticate();
        daoUser = new DAOUser(userPseudo);
        view.setVisible(true);
    }

    public String authenticate() {
        while (true) {
            // Show the account chooser dialog
            JDialogAccountChooser accountChooserDialog = view.showAccountChooserDialog();

            if (accountChooserDialog.isConfirmed()) {
                if (accountChooserDialog.getPseudo().isEmpty() || accountChooserDialog.getPassword().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Pseudo or password cannot be empty.");
                } else {
                    if (accountChooserDialog.isCreatingAccount()) {
                        // Create a new user
                        authenticator.addUsers(accountChooserDialog.getPseudo(), accountChooserDialog.getPassword());

                        return accountChooserDialog.getPseudo();
                    } else {
                        // Login with an existing user
                        boolean authenticationSuccess = authenticator.authenticate(accountChooserDialog.getPseudo(), accountChooserDialog.getPassword());

                        if (authenticationSuccess) {
                            // Correct credentials
                            return accountChooserDialog.getPseudo();
                        } else {
                            // Incorrect credentials
                            JOptionPane.showMessageDialog(view, "Pseudo or password incorrect.");
                        }
                    }
                }
            } else {
                // Clicked on 'Cancel' or closed the dialog
                System.exit(0);
            }
        }
    }

    public void run() {
        view.run();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("HOME")) {
            view.showHomePanel();
        } else if (e.getActionCommand().equals("SEARCH")) {
            view.showSearchPanel();
        } else if (e.getActionCommand().equals("FAVORITES")) {
            view.showPlaylist(0);
        } else if (e.getActionCommand().equals("SWITCH_ACCOUNT")) {
            view.setVisible(false);
            authenticate();
            view.setVisible(true);
//            view.updateAll();
        } else if (e.getActionCommand().equals("SETTINGS")) {
            view.showSettings();
        } else if (e.getActionCommand().equals("SONG_DETAILS")) {
            view.showSongDetails();
        } else if (e.getActionCommand().equals("PLAYLIST_SETTINGS")) {
            view.showPlaylistSettings();
        } else if (e.getActionCommand().equals("ADD_TO_PLAYLIST")) {
            view.addCurrentSongToPlaylist();
        } else {
            view.showMessage("Button not implemented !");
        }
    }
}
