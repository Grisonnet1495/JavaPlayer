package com.javaPlayer.project.view.GUI;

import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.controller.MainControllerActions;

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

    private String selectedPlaylistTitle = null;
    private MainController controller;

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

    public void clearSelectedPlaylistTitle() {
        selectedPlaylistTitle = null;
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
        JButton playlistButton = new JButton(playlistTitle.substring(0, 1));
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

        playlistButton.setActionCommand(MainControllerActions.PLAYLIST_VIEW);
        playlistButton.addActionListener(e -> {
            selectedPlaylistTitle = playlistTitle;
        });
        playlistButton.addActionListener(controller);


        return playlistButtonPanel;
    }
}
