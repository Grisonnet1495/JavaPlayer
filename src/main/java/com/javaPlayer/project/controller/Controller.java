package com.javaPlayer.project.controller;

import com.javaPlayer.project.model.DataAccessLayer;
import com.javaPlayer.project.view.GUI.JFrameMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class Controller implements ActionListener {
    private DataAccessLayer model;
    private JFrameMainWindow view;

    public Controller(JFrameMainWindow view, DataAccessLayer model) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
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
