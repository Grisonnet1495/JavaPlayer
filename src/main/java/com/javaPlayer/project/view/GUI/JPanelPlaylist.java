package com.javaPlayer.project.view.GUI;

import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.model.entity.Playlist;

import javax.swing.*;

public class JPanelPlaylist extends JPanel {
    public JPanel mainPanel;
    private JScrollBar scrollBar1;
    private JTable songTable;
    private JPanel playlistInfoPanel;
    private JPanel songTableJPanel;
    private JPanel playlistTilePanel;
    private JLabel playlistTitleLabel;
    private JPanel playlistIconOutPanel;
    public JButton playlistSettingsButton;

    void setController(MainController c) {
        playlistSettingsButton.setActionCommand("PLAYLIST_SETTINGS");
        playlistSettingsButton.addActionListener(c);
    }

    void updatePlaylist(Playlist playlist) {
        playlistTitleLabel.setText(playlist.getTitle());
        // To do : update the table
    }
}
