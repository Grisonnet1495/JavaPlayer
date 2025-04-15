package com.javaPlayer.project.view.GUI;

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
    ArrayList<String> recentPlaylistsList = new ArrayList<>();
    ArrayList<String> allPlaylistsList = new ArrayList<>();

    public JPanelHome() {
        recentPlaylistsContentPanel.setLayout(new WrapLayout(FlowLayout.LEFT));
        allPlaylistsContentPanel.setLayout(new WrapLayout(FlowLayout.LEFT));
        updateRecentPlaylists();
        updateAllPlaylists();
    }

    void updateRecentPlaylists() {
        recentPlaylistsList.clear();
        recentPlaylistsContentPanel.removeAll();

        // Note : Temporary. It needs to extract recent playlists.
        recentPlaylistsList.add("Playlist 1");
        recentPlaylistsList.add("Playlist 2");
        recentPlaylistsList.add("Playlist 3");
        recentPlaylistsList.add("Playlist 4");

        for (String element : recentPlaylistsList) {
            // Creation of the components
            JPanel playlistButtonPanel = new JPanel();
            playlistButtonPanel.setLayout(new BoxLayout(playlistButtonPanel, BoxLayout.Y_AXIS));
            JButton playlistButton = new JButton(String.valueOf(element.charAt(0)));
            JLabel playlistLabel = new JLabel(element);

            recentPlaylistsContentPanel.add(playlistButtonPanel);

            // Setup of the components
            playlistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            Dimension size = new Dimension(100, 120);
            playlistButton.setPreferredSize(size);
            playlistButton.setMaximumSize(size);
            playlistButton.setMinimumSize(size);

            playlistLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            playlistLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Adding the components
            playlistButtonPanel.add(playlistButton);
            playlistButtonPanel.add(playlistLabel);
            recentPlaylistsContentPanel.add(playlistButtonPanel);
        }
    }

    void updateAllPlaylists() {
        allPlaylistsList.clear();
        allPlaylistsContentPanel.removeAll();

        // Note : Temporary. It needs to extract all playlists.
        allPlaylistsList.add("Playlist 1");
        allPlaylistsList.add("Playlist 2");
        allPlaylistsList.add("Playlist 3");
        allPlaylistsList.add("Playlist 4");
        allPlaylistsList.add("Playlist 5");
        allPlaylistsList.add("Playlist 6");
        allPlaylistsList.add("Playlist 7");
        allPlaylistsList.add("Playlist 8");
        allPlaylistsList.add("Playlist 9");
        allPlaylistsList.add("Playlist 10");
        allPlaylistsList.add("Playlist 11");
        allPlaylistsList.add("Playlist 12");

        for (String song : allPlaylistsList) {
            // Creation of the components
            JPanel playlistButtonPanel = new JPanel();
            playlistButtonPanel.setLayout(new BoxLayout(playlistButtonPanel, BoxLayout.Y_AXIS));
            JButton playlistButton = new JButton(String.valueOf(song.charAt(0)));
            JLabel playlistLabel = new JLabel(song);

            allPlaylistsContentPanel.add(playlistButtonPanel);

            // Setup of the components
            playlistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            Dimension size = new Dimension(100, 120);
            playlistButton.setPreferredSize(size);
            playlistButton.setMaximumSize(size);
            playlistButton.setMinimumSize(size);

            playlistLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            playlistLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Adding the components
            playlistButtonPanel.add(playlistButton);
            playlistButtonPanel.add(playlistLabel);
            allPlaylistsContentPanel.add(playlistButtonPanel);
        }
    }
}
