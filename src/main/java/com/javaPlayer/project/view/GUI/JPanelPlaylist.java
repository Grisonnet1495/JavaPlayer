package com.javaPlayer.project.view.GUI;

import com.javaPlayer.project.controller.Controller;
import com.javaPlayer.project.controller.ControllerActions;
import com.javaPlayer.project.model.entity.Playlist;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;

public class JPanelPlaylist extends JPanel {
    public JPanel mainPanel;
    private JPanel playlistInfoPanel;
    private JPanel songTablePanel;
    private JTablePlaylist songTable;
    private JPanel playlistTilePanel;
    private JLabel playlistTitleLabel;
    private JPanel playlistIconOutPanel;
    public JButton playlistSettingsButton;
    private JScrollPane songTableScrollPane;

    private Controller controller;

    public JPanelPlaylist() {
        songTableScrollPane.setBorder(BorderFactory.createEmptyBorder());
    }

    void setController(Controller c) {
        this.controller = c;
        playlistSettingsButton.setActionCommand("PLAYLIST_SETTINGS");
        playlistSettingsButton.addActionListener(c);

        // By ChatGPT
        songTable.getSelectionModel().addListSelectionListener(e -> {
            // If it isn't just a screen update
            if (!e.getValueIsAdjusting()) {
                int selectedRow = songTable.getSelectedRow();

                // If a row is selected
                if (selectedRow != -1) {
                    int songId = (int) songTable.getValueAt(selectedRow, 0);

                    if (controller != null) {
                        ActionEvent event = new ActionEvent(songId, ActionEvent.ACTION_PERFORMED, ControllerActions.PLAY_SELECTED_SONG);
                        controller.actionPerformed(event);
                    }
                }
            }
        });
    }

    void updatePlaylist(Playlist playlist) {
        // Update playlist title
        playlistTitleLabel.setText(playlist.getTitle());

        // Update playlist icon
        int targetSize = 100;

        byte[] buttonIcon = playlist.getIcon();

        if (buttonIcon != null) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(buttonIcon);
                BufferedImage bufferedImage = ImageIO.read(bais);
                Image scaledImage = bufferedImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
                playlistSettingsButton.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                throw new RuntimeException("Error while loading the playlist icon", e);
            }
        } else {
            playlistSettingsButton.setText(playlist.getTitle().substring(0, 1));
        }

        // Update song table
        String[] columnTitles = {"N°", "Title", "Artist", "Genre", "Duration", "Added date"};
        Object[][] tableData = new Object[playlist.getSongList().size()][columnTitles.length];

        for (int i = 0; i < playlist.getSongList().size(); i++) {
            tableData[i][0] = playlist.getSongList().get(i).getId();
            tableData[i][1] = playlist.getSongList().get(i).getTitle();
            tableData[i][2] = playlist.getSongList().get(i).getArtist();
            tableData[i][3] = playlist.getSongList().get(i).getGenre();
            tableData[i][4] = playlist.getSongList().get(i).getFormattedDuration();
            tableData[i][5] = playlist.getSongList().get(i).getAddedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnTitles);

        songTable.setModel(tableModel);
    }

    private void createUIComponents() {
        songTable = new JTablePlaylist(new DefaultTableModel());
    }
}
