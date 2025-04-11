package com.javaPlayer.project.view.GUI;

//import com.formdev.flatlaf.FlatMacLightLaf;
import com.formdev.flatlaf.themes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class mainWindowGUI extends JFrame implements ActionListener {
    // Main panel
    private JPanel mainPanel;

    // Menu bar
    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenu editMenu;
    private final JMenu songMenu;
    private final JMenu playlistMenu;
    private final JMenuItem openSongMenuItem;
    private final JMenuItem createBackupMenuItem;
    private final JMenuItem settingsMenuItem;
    private final JMenuItem exportPlaylistMenuItem;
    private final JMenuItem addSongToFavoritesMenuItem;
    private final JMenuItem removeSongFromFavoritesMenuItem;
    private final JMenuItem addSongToPlaylistMenuItem;
    private final JMenuItem removeSongFromPlaylistMenuItem;
    private final JMenuItem searchSongMenuItem;
    private final JMenuItem createPlaylistMenuItem;
    private final JMenuItem deletePlaylistMenuItem;
    private final JMenuItem editPlaylistMenuItem;


    // Left Menu Panel
    private JButton homeButton;
    private JButton searchButton;
    private JButton favoritesButton;
    private JPanel leftMenuPanel;

    // Song panel
    private JPanel songPanel;
    private JScrollPane contentScrollPane;
    private JSlider timeSlider;
    private JPanel songTimeActionsPanel;
    private JPanel songActionsPanel;
    private JButton pausePlayButton;
    private JButton previousButton;
    private JButton nextButton;
    private JButton randomButton;
    private JButton loopButton;
    private JLabel elapsedTimeLabel;
    private JLabel remainingTimeLabel;
    private JLabel playlistsLabel;
    private JButton songIconButton;
    private JLabel songTitleLabel;
    private JLabel songArtistLabel;
    private JButton addToFavoritesButton;
    private JButton addToPlaylistButton;
    private JPanel playlistActionsPanel;
    private JPanel songIconInfoPanel;
    private JPanel songInfoPanel;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    // Add the different content panels
    private HomePanelGUI homePanel;
    private PlaylistPanelGUI playlistPanel;
    private SearchPanelGUI searchPanel;

    public mainWindowGUI() {
        // Set the window
        super("JavaPlayer - Playlist");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200,1000);
        this.setMinimumSize(new Dimension(1200, 1000));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setContentPane(mainPanel);

        mainPanel.setMinimumSize(new Dimension(1000, 500));

        // Set the menu bar
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        // Set the different menus
        fileMenu = new JMenu("Files");
        editMenu = new JMenu ("Edit"); // Note : Useful ?
        songMenu = new JMenu ("Song");
        playlistMenu = new JMenu ("Playlist");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(songMenu);
        menuBar.add(playlistMenu);

        // Set the menu items for the 'Files' menu
        openSongMenuItem = new JMenuItem("Open file");
        createBackupMenuItem = new JMenuItem("Create a backup");
        fileMenu.add(openSongMenuItem);
        fileMenu.add(createBackupMenuItem);
        openSongMenuItem.addActionListener(this);
        createBackupMenuItem.addActionListener(this);

        // Set the menu items for the 'Edit' menu
        settingsMenuItem = new JMenuItem("Settings");
        editMenu.add(settingsMenuItem);
        settingsMenuItem.addActionListener(this);

        // Set the menu items for the 'Song' menu
        addSongToFavoritesMenuItem = new JMenuItem("Add to favorites");
        removeSongFromFavoritesMenuItem = new JMenuItem("Remove from favorites");
        addSongToPlaylistMenuItem = new JMenuItem("Add to playlist");
        removeSongFromPlaylistMenuItem = new JMenuItem("Remove from playlist");
        searchSongMenuItem = new JMenuItem("Search for a song");
        songMenu.add(addSongToFavoritesMenuItem);
        songMenu.add(removeSongFromFavoritesMenuItem);
        songMenu.add(addSongToPlaylistMenuItem);
        songMenu.add(removeSongFromPlaylistMenuItem);
        songMenu.add(searchSongMenuItem);
        addSongToFavoritesMenuItem.addActionListener(this);
        addSongToPlaylistMenuItem.addActionListener(this);
        removeSongFromFavoritesMenuItem.addActionListener(this);
        addSongToPlaylistMenuItem.addActionListener(this);
        removeSongFromPlaylistMenuItem.addActionListener(this);
        searchSongMenuItem.addActionListener(this);

        // Set the menu items for the 'Playlist' menu
        createPlaylistMenuItem = new JMenuItem("Add to playlist");
        deletePlaylistMenuItem = new JMenuItem("Remove from playlist");
        editPlaylistMenuItem = new JMenuItem("Edit playlist");
        exportPlaylistMenuItem = new JMenuItem("Export playlist");
        playlistMenu.add(createPlaylistMenuItem);
        playlistMenu.add(deletePlaylistMenuItem);
        playlistMenu.add(editPlaylistMenuItem);
        playlistMenu.add(exportPlaylistMenuItem);
        createPlaylistMenuItem.addActionListener(this);
        deletePlaylistMenuItem.addActionListener(this);
        editPlaylistMenuItem.addActionListener(this);
        exportPlaylistMenuItem.addActionListener(this);

        // Add remaining action listener

        homeButton.addActionListener(this);
        searchButton.addActionListener(this);
        favoritesButton.addActionListener(this);
        pausePlayButton.addActionListener(this);
        previousButton.addActionListener(this);
        nextButton.addActionListener(this);
        randomButton.addActionListener(this);
        loopButton.addActionListener(this);
        addToPlaylistButton.addActionListener(this);
        addToFavoritesButton.addActionListener(this);
        songIconButton.addActionListener(this);

        // Delete the border of some buttons
        randomButton.setBorderPainted(false);
        previousButton.setBorderPainted(false);
        nextButton.setBorderPainted(false);
        randomButton.setBorderPainted(false);
        loopButton.setBorderPainted(false);
        addToFavoritesButton.setBorderPainted(false);
        addToPlaylistButton.setBorderPainted(false);

        // Set the UI of timeSlider
        timeSlider.setUI(new javax.swing.plaf.basic.BasicSliderUI(timeSlider) {
            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(229, 158, 221)); // Track color
                g2.fillRect(trackRect.x, trackRect.y + trackRect.height / 2 - 1, trackRect.width, 3);
            }

            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(160, 43, 147)); // Cursor color
                g2.fillOval(thumbRect.x, thumbRect.y + thumbRect.height / 2 - 4, 10, 10); // Round cursor
            }
        });

        // Create the layout for the main content
        cardLayout = new CardLayout(); // Note : On peut raccourcir ?
        contentPanel = new JPanel(cardLayout);
        contentPanel.setPreferredSize(new Dimension(contentScrollPane.getPreferredSize().width, contentScrollPane.getPreferredSize().height));


        homePanel = new HomePanelGUI();
        playlistPanel = new PlaylistPanelGUI();
        searchPanel = new SearchPanelGUI();

        // Add the main content
        contentPanel.add(homePanel.mainPanel, "Home");
        contentPanel.add(playlistPanel.mainPanel, "Playlist");
        contentPanel.add(searchPanel.mainPanel, "Search");

        contentScrollPane.setViewportView(contentPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) {
//            cardLayout.show(contentPanel, "Home"); // Note : Do it need to be destroyed and re-created ?
//
//            homePanel.recentPlaylistsContentPanel.revalidate();
//            homePanel.recentPlaylistsContentPanel.repaint();
            homePanel.updateRecentPlaylists();
            homePanel.updateAllPlaylists();
            contentPanel.remove(homePanel.mainPanel);
            contentPanel.add(homePanel.mainPanel, "Home");
            cardLayout.show(contentPanel, "Home"); // Note : Do it need to be destroyed and re-created ?
        } else if (e.getSource() == searchButton) {
            cardLayout.show(contentPanel, "Search");
        } else if (e.getSource() == favoritesButton) {
            cardLayout.show(contentPanel, "Playlist");
        } else if (e.getSource() == settingsMenuItem) {
            // Create the settings dialog box
            JDialog settingsDialog = new JDialog(this, true);
            settingsDialog.setTitle("Settings");

            // Set the properties of the dialog box
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int) ((float) screenSize.width / 1.5);
            int height = (int) ((float) screenSize.height / 1.5);
            settingsDialog.setSize(new Dimension(width, height));
            settingsDialog.setResizable(false);

            int x = (screenSize.width - settingsDialog.getWidth()) / 2;
            int y = (screenSize.height - settingsDialog.getHeight()) / 2;
            settingsDialog.setLocation(x, y);

            settingsDialog.setContentPane(new SettingsPanelGUI().mainPanel);
            settingsDialog.setVisible(true);
            settingsDialog.dispose();
        } else if (e.getSource() == songIconButton) {
            // Create the settings dialog box
            JDialog songDetailsDialog = new JDialog(this, true);
            songDetailsDialog.setTitle("Song details");

            // Set the properties of the dialog box
            songDetailsDialog.setSize(new Dimension(500, 250));
            songDetailsDialog.setResizable(false);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - songDetailsDialog.getWidth()) / 2;
            int y = (screenSize.height - songDetailsDialog.getHeight()) / 2;
            songDetailsDialog.setLocation(x, y);

            songDetailsDialog.setContentPane(new SongDetailsPanelGUI().mainPanel);
            songDetailsDialog.setVisible(true);
            songDetailsDialog.dispose();

        } else if (e.getSource() == addToFavoritesButton) {
            // Add song to favorites
            // Change icon
        } else if (e.getSource() == addToPlaylistButton) {
            // Add song to playlist
        } else {
            JOptionPane.showMessageDialog(this, "Button not implemented !");
        }
    }

    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        mainWindowGUI window = new mainWindowGUI();
        window.setVisible(true);
    }
}
