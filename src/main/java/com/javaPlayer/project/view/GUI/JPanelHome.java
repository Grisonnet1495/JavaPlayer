package com.javaPlayer.project.view.GUI;

import com.javaPlayer.project.controller.Controller;
import com.javaPlayer.project.controller.ControllerActions;
import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.utils.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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

    private Controller controller;

    public JPanelHome() {
        recentPlaylistsScrollPane.setBorder(null);
        allPlaylistsScrollPane.setBorder(null);
        recentPlaylistsContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        allPlaylistsContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    void setController(Controller c) {
        this.controller = c;
    }

    void updateRecentPlaylists(ArrayList<Playlist> playlistsList) {
        recentPlaylistsContentPanel.removeAll();

        if (playlistsList.isEmpty()) {
            recentPlaylistsContentPanel.add(noRecentPlaylistsLabel);
        } else {
            for (Playlist p : playlistsList) {
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
        JPanel playlistButtonPanel = new JPanel();
        playlistButtonPanel.setLayout(new BoxLayout(playlistButtonPanel, BoxLayout.Y_AXIS));

        JButton playlistButton = new JButton();
        playlistButton.setBackground(Color.WHITE);
        playlistButton.setOpaque(true);
        playlistButton.setBorderPainted(false);
        playlistButton.setFocusPainted(false);

        int targetSize = 212;
        Icon finalButtonIcon;

        byte[] buttonIcon = playlist.getIcon();
        if (buttonIcon != null) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(buttonIcon);
                BufferedImage bufferedImage = ImageIO.read(bais);
                Image scaledImage = bufferedImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
                finalButtonIcon = new ImageIcon(scaledImage);
                playlistButton.setIcon(finalButtonIcon);
            } catch (Exception e) {
                throw new RuntimeException("Error while loading the playlist icon: " + e.getMessage());
            }
        } else {
            try {
                BufferedImage defaultImage = ImageIO.read(Objects.requireNonNull(getClass().getResource(Constants.DEFAULT_PLAYLIST_ICON)));
                Image scaledDefaultImage = defaultImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
                finalButtonIcon = new ImageIcon(scaledDefaultImage);
                playlistButton.setIcon(finalButtonIcon);
            } catch (IOException e) {
                throw new RuntimeException("Error while loading the default playlist icon: " + e.getMessage());
            }
        }

        JLabel playlistLabel = new JLabel(playlist.getTitle());
        playlistLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playlistLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playlistLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        playlistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension buttonSize = new Dimension(targetSize, targetSize);
        playlistButton.setPreferredSize(buttonSize);
        playlistButton.setMaximumSize(buttonSize);
        playlistButton.setMinimumSize(buttonSize);
        playlistButton.setForeground(Color.WHITE);
        playlistButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));

        int panelSize = 250;
        playlistButtonPanel.setPreferredSize(new Dimension(panelSize, panelSize));
        playlistButtonPanel.setMaximumSize(new Dimension(panelSize, panelSize));
        playlistButtonPanel.setMinimumSize(new Dimension(panelSize, panelSize));

        playlistButtonPanel.add(playlistButton);
        playlistButtonPanel.add(playlistLabel);

        allPlaylistsContentPanel.add(playlistButtonPanel);
        allPlaylistsContentPanel.revalidate();
        allPlaylistsContentPanel.repaint();

        Color hoverColor = new Color(0xA02B93);
        Icon finalIcon = finalButtonIcon; // Create a local copy of the icon
        String playlistTitle = playlist.getTitle();

        playlistButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playlistLabel.setVisible(false);
                playlistButton.setBackground(hoverColor);
                playlistButton.setIcon(null);
                playlistButton.setText(playlistTitle);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playlistLabel.setVisible(true);
                playlistButton.setBackground(Color.WHITE);
                playlistButton.setIcon(finalIcon);
                playlistButton.setText("");
            }
        });

        // Action du bouton
        playlistButton.addActionListener(e -> {
            if (controller != null) {
                ActionEvent event = new ActionEvent(playlist.getId(), ActionEvent.ACTION_PERFORMED, ControllerActions.PLAYLIST_VIEW);
                controller.actionPerformed(event);
            }
        });

        return playlistButtonPanel;
    }
}
