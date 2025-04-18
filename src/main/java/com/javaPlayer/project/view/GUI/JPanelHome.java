package com.javaPlayer.project.view.GUI;

import com.javaPlayer.project.controller.MainController;

import javax.swing.*;
import java.awt.*;
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
    private MainController controller;

    public JPanelHome() {
        recentPlaylistsScrollPane.setBorder(null);
        allPlaylistsScrollPane.setBorder(null);
        recentPlaylistsContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        allPlaylistsContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//        updateRecentPlaylists();
//        updateAllPlaylists();
    }

    void setController(MainController c) {
        this.controller = c;
    }

    void updateRecentPlaylists(ArrayList<String> playlistsTitleList) {
        recentPlaylistsContentPanel.removeAll();

        for (String playlistTitle : playlistsTitleList) {
            // Creation of the components
            JPanel playlistButtonPanel = createButtonPanel(playlistTitle);

            recentPlaylistsContentPanel.add(playlistButtonPanel);
        }

        recentPlaylistsContentPanel.revalidate();
        recentPlaylistsContentPanel.repaint();
    }

    void updateAllPlaylists(ArrayList<String> playlistsTitleList) {
        allPlaylistsContentPanel.removeAll();

        for (String playlistTitle : playlistsTitleList) {
            JPanel playlistButtonPanel = createButtonPanel(playlistTitle);
            allPlaylistsContentPanel.add(playlistButtonPanel);
        }

        allPlaylistsContentPanel.revalidate();
        allPlaylistsContentPanel.repaint();
    }

    JPanel createButtonPanel(String playlistTitle) {
        // Creation of the components
        JPanel playlistButtonPanel = new JPanel();
        playlistButtonPanel.setLayout(new BoxLayout(playlistButtonPanel, BoxLayout.Y_AXIS));
        JButton playlistButton = new JButton(String.valueOf(playlistTitle.charAt(0)));
        JLabel playlistLabel = new JLabel(playlistTitle);

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

        playlistButton.addActionListener(controller);
        playlistButton.setActionCommand(playlistTitle);

        return playlistButtonPanel;
    }
}
