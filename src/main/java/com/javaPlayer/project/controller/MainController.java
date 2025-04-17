package com.javaPlayer.project.controller;

import com.javaPlayer.project.model.authentication.FileAuthenticator;
import com.javaPlayer.project.view.GUI.JDialogAccountChooser;
import com.javaPlayer.project.view.GUI.JFrameMainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class MainController implements ActionListener {
//    private DataAccessLayer model;
//    private DAOAuthenticator authenticationModel;
    private JFrameMainWindow view;
    private FileAuthenticator fileAuthenticator;


    public MainController(JFrameMainWindow view) {
//        this.authenticationModel = authenticationModel;
        this.view = view;
        this.view.setController(this);
        this.fileAuthenticator = new FileAuthenticator();

        authenticate();
    }

    public void authenticate() {

        boolean authenticationSuccess = false;

        while (!authenticationSuccess) {
            // Création du JDialog
            JDialogAccountChooser accountChooserDialog = new JDialogAccountChooser();
            accountChooserDialog.setTitle("Login or create an account");
            accountChooserDialog.setModal(true);
            accountChooserDialog.setLocationRelativeTo(null);
            accountChooserDialog.setVisible(true);

            if (accountChooserDialog.isConfirmed()) {
                if (accountChooserDialog.getPseudo().isEmpty() || accountChooserDialog.getPassword().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Pseudo or password cannot be empty.");
                }
                else {
                    if (accountChooserDialog.isCreatingAccount()) {
                        // Create a new user
                        fileAuthenticator.addUsers(accountChooserDialog.getPseudo(), accountChooserDialog.getPassword());
                        authenticationSuccess = true;
                    } else {
                        authenticationSuccess = fileAuthenticator.authenticate(accountChooserDialog.getPseudo(), accountChooserDialog.getPassword());
                        if (!authenticationSuccess) {
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
