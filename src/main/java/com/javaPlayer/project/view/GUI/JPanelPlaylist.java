package com.javaPlayer.project.view.GUI;

import com.javaPlayer.project.controller.MainController;

import javax.swing.*;

public class JPanelPlaylist extends JPanel {
    public JPanel mainPanel;
    private JScrollBar scrollBar1;
    private JTable songTable;
    private JPanel PlaylistInfoPanel;
    private JPanel songTableJPanel;
    private JPanel playlistTilePanel;
    private JLabel PlaylistTitleLabel;
    private JPanel playlistIconOutPanel;
    public JButton playlistSettingsButton;

    void setController(MainController c) {
        playlistSettingsButton.setActionCommand("PLAYLIST_SETTINGS");
        playlistSettingsButton.addActionListener(c);
    }
}
