package com.javaPlayer.project.view.GUI;

import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.model.entity.Playlist;

import javax.swing.*;
import java.awt.*;

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

        String[] columnNames = {"N°", "Title", "Artist", "Genre", "Duration"};
        Object[][] data = new Object[playlist.getSongList().size()][columnNames.length];

        for (int i = 0; i < playlist.getSongList().size(); i++) {
            data[i][0] = playlist.getSongList().get(i).getId();
            data[i][1] = playlist.getSongList().get(i).getTitle();
            data[i][2] = playlist.getSongList().get(i).getArtist().getPseudo();
            data[i][3] = playlist.getSongList().get(i).getGenre();
            data[i][4] = playlist.getSongList().get(i).getDuration();
        }

        songTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        visual();
    }

    private void visual() {
        songTable.setRowHeight(36);
        songTable.setFont(new Font("Quicksand", Font.ITALIC, 15));
        songTable.setForeground(Color.BLACK);
    }
}

