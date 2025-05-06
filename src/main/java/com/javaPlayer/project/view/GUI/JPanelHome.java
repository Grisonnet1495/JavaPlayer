package com.javaPlayer.project.view.GUI;

import com.javaPlayer.project.controller.Controller;
import com.javaPlayer.project.controller.ControllerActions;
import com.javaPlayer.project.model.entity.Playlist;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class JPanelHome extends JPanel {
    public JPanel mainPanel;
    private JLabel recentPlaylistsLabel;
    private JLabel allPlaylistsLabel;
    private JLabel noRecentPlaylistsLabel;
    private JPanel recentPlaylistsTitlePanel;
    public JPanel recentPlaylistsContentPanel;
    private JPanel allPlaylistsTitlePanel;
    public JPanel allPlaylistsContentPanel;
    private JLabel noAllPlaylistsLabel;
    private JScrollPane recentPlaylistsScrollPane;
    private JScrollPane allPlaylistsScrollPane;

    private String selectedPlaylistTitle = null;
    private Controller controller;

    public JPanelHome() {
        recentPlaylistsScrollPane.setBorder(null);
        allPlaylistsScrollPane.setBorder(null);
        recentPlaylistsContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        allPlaylistsContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//        updateRecentPlaylists();
//        updateAllPlaylists();
    }

    public String getSelectedPlaylistTitle() {
        return selectedPlaylistTitle;
    }

//    public void clearSelectedPlaylistTitle() {
//        selectedPlaylistTitle = null;
//    }

    void setController(Controller c) {
        this.controller = c;
    }

    void updateRecentPlaylists(ArrayList<Playlist> playlistsList) {
        recentPlaylistsContentPanel.removeAll();

        if (playlistsList.isEmpty()) {
            recentPlaylistsContentPanel.add(noRecentPlaylistsLabel);
        } else {
            for (Playlist p : playlistsList) {
                // Creation of the components
                JPanel playlistButtonPanel = createButtonPanel(p);

                recentPlaylistsContentPanel.add(playlistButtonPanel);
            }
        }

        recentPlaylistsContentPanel.revalidate();
        recentPlaylistsContentPanel.repaint();
    }

    void updateAllPlaylists(ArrayList<Playlist> playlistsList) {
        allPlaylistsContentPanel.removeAll();

        if (playlistsList.isEmpty()) {
            allPlaylistsContentPanel.add(noAllPlaylistsLabel);
        } else {
            for (Playlist p : playlistsList) {
                JPanel playlistButtonPanel = createButtonPanel(p);
                allPlaylistsContentPanel.add(playlistButtonPanel);
            }
        }

        allPlaylistsContentPanel.revalidate();
        allPlaylistsContentPanel.repaint();
    }

    JPanel createButtonPanel(Playlist playlist) {
        // Creation of the components
        JPanel playlistButtonPanel = new JPanel();
        playlistButtonPanel.setLayout(new BoxLayout(playlistButtonPanel, BoxLayout.Y_AXIS));

        JButton playlistButton;

        // Set the icon of the button
        byte[] buttonIcon = playlist.getIcon();
        if (buttonIcon != null) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(buttonIcon);
                BufferedImage bufferedImage = ImageIO.read(bais);
                playlistButton = new JButton();
                playlistButton.setIcon(new ImageIcon(bufferedImage));
            } catch (Exception e) {
                throw new RuntimeException("Error while loading the playlist icon", e);
            }
        } else {
            playlistButton = new JButton(playlist.getTitle().substring(0, 1));
        }

        // Set the playlist label
        JLabel playlistLabel = new JLabel(playlist.getTitle());

        // Add the button panel to the all playlists content panel
        allPlaylistsContentPanel.add(playlistButtonPanel);

        // Setup of the components
        playlistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension size = new Dimension(180, 230);
        playlistButton.setPreferredSize(size);
        playlistButton.setMaximumSize(size);
        playlistButton.setMinimumSize(size);

        playlistLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playlistLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Adding the components
        playlistButtonPanel.add(playlistButton);
        playlistButtonPanel.add(playlistLabel);

        allPlaylistsContentPanel.revalidate();
        allPlaylistsContentPanel.repaint();

        playlistButton.addActionListener(e -> {
            selectedPlaylistTitle = playlist.getTitle();

            if (controller != null) {
                ActionEvent event = new ActionEvent(playlist.getId(), ActionEvent.ACTION_PERFORMED, ControllerActions.PLAYLIST_VIEW);
                controller.actionPerformed(event);
            }
        });

        return playlistButtonPanel;
    }
}
