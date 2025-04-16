package com.javaPlayer.project.view.GUI;

//import com.formdev.flatlaf.FlatMacLightLaf;
import com.formdev.flatlaf.themes.*;
import com.javaPlayer.project.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class JFrameMainWindow extends JFrame implements ViewMainWindow {
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
    private JPanelHome homePanel = new JPanelHome();
    private JPanelPlaylist playlistPanel = new JPanelPlaylist();
    private JPanelSearch searchPanel = new JPanelSearch();

    public JFrameMainWindow() {
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

        // Set the menu items for the 'Edit' menu
        settingsMenuItem = new JMenuItem("Settings");
        editMenu.add(settingsMenuItem);

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

        // Set the menu items for the 'Playlist' menu
        createPlaylistMenuItem = new JMenuItem("Create playlist");
        deletePlaylistMenuItem = new JMenuItem("Delete playlist");
        editPlaylistMenuItem = new JMenuItem("Edit playlist");
        exportPlaylistMenuItem = new JMenuItem("Export playlist");
        playlistMenu.add(createPlaylistMenuItem);
        playlistMenu.add(deletePlaylistMenuItem);
        playlistMenu.add(editPlaylistMenuItem);
        playlistMenu.add(exportPlaylistMenuItem);

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
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.setPreferredSize(new Dimension(contentScrollPane.getPreferredSize().width, 10000));

        // Add the main content
        contentPanel.add(homePanel.mainPanel, "Home");
        contentPanel.add(playlistPanel.mainPanel, "Playlist");
        contentPanel.add(searchPanel.mainPanel, "Search");

        contentScrollPane.setViewportView(contentPanel);
    }

    @Override
    public void showHomePanel() {
        homePanel.updateRecentPlaylists();
        homePanel.updateAllPlaylists();
        contentPanel.remove(homePanel.mainPanel);
        contentPanel.add(homePanel.mainPanel, "Home");
        cardLayout.show(contentPanel, "Home"); // Note : Do it need to be destroyed and re-created ?
    }

    @Override
    public void showSearchPanel() {
        cardLayout.show(contentPanel, "Search");
    }

    @Override
    public void showPlaylist(int playlistId) {
        cardLayout.show(contentPanel, "Playlist");
    }

    @Override
    public void showSettings() {
        JDialogSettings settingsDialog = new JDialogSettings(this,true);

        // Set the properties of the dialog box
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) ((float) screenSize.width / 1.5);
        int height = (int) ((float) screenSize.height / 1.5);
        settingsDialog.setSize(new Dimension(width, height));
        settingsDialog.setResizable(false);

        int x = (screenSize.width - settingsDialog.getWidth()) / 2;
        int y = (screenSize.height - settingsDialog.getHeight()) / 2;
        settingsDialog.setLocation(x, y);

        settingsDialog.setVisible(true);
        settingsDialog.dispose();
    }

    @Override
    public void showSongDetails() {
        JDialogSongDetails songDetailsDialog = new JDialogSongDetails(this,true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - songDetailsDialog.getWidth()) / 2;
        int y = (screenSize.height - songDetailsDialog.getHeight()) / 2;
        songDetailsDialog.setLocation(x, y);

        songDetailsDialog.setVisible(true);
        songDetailsDialog.dispose();
    }

    @Override
    public void showPlaylistSettings()
    {
        // Create the settings dialog box
        JDialogPlaylistSettings playlistSettingsDialog = new JDialogPlaylistSettings(this, true);

        // Set the properties of the dialog box
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        playlistSettingsDialog.setSize(new Dimension(500, 300));
        playlistSettingsDialog.setResizable(false);

        int x = (screenSize.width - playlistSettingsDialog.getWidth()) / 2;
        int y = (screenSize.height - playlistSettingsDialog.getHeight()) / 2;
        playlistSettingsDialog.setLocation(x, y);

        playlistSettingsDialog.setVisible(true);
        playlistSettingsDialog.dispose();
    }

    @Override
    public void toggleFavoritesForCurrentSong() {
        // Toggle favorites
    }

    @Override
    public void addCurrentSongToPlaylist() {
        JDialogAddToPlaylist addToPlaylistDialog = new JDialogAddToPlaylist(this, true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - addToPlaylistDialog.getWidth()) / 2;
        int y = (screenSize.height - addToPlaylistDialog.getHeight()) / 2;
        addToPlaylistDialog.setLocation(x, y);

        addToPlaylistDialog.setVisible(true);
        // Note : Retrieve the data
        addToPlaylistDialog.dispose();
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void run() {
        setVisible(true);
    }

    @Override
    public void setController(Controller c) {
        // Menu items
        openSongMenuItem.setActionCommand("OPEN_SONG");
        openSongMenuItem.addActionListener(c);
        settingsMenuItem.setActionCommand("SETTINGS");
        settingsMenuItem.addActionListener(c);
        createBackupMenuItem.setActionCommand("CREATE_BACKUP");
        createBackupMenuItem.addActionListener(c);
        addSongToFavoritesMenuItem.setActionCommand("ADD_TO_FAVORITES");
        addSongToFavoritesMenuItem.addActionListener(c);
        addSongToPlaylistMenuItem.setActionCommand("ADD_TO_PLAYLIST");
        addSongToPlaylistMenuItem.addActionListener(c);
        removeSongFromFavoritesMenuItem.setActionCommand("REMOVE_SONG_FROM_FAVORITES");
        removeSongFromFavoritesMenuItem.addActionListener(c);
        removeSongFromPlaylistMenuItem.setActionCommand("REMOVE_SONG_FROM_PLAYLIST");
        removeSongFromPlaylistMenuItem.addActionListener(c);
        searchSongMenuItem.setActionCommand("SEARCH_SONG");
        searchSongMenuItem.addActionListener(c);
        createPlaylistMenuItem.setActionCommand("CREATE_PLAYLIST");
        createPlaylistMenuItem.addActionListener(c);
        deletePlaylistMenuItem.setActionCommand("DELETE_PLAYLIST");
        deletePlaylistMenuItem.addActionListener(c);
        editPlaylistMenuItem.setActionCommand("EDIT_PLAYLIST");
        editPlaylistMenuItem.addActionListener(c);
        exportPlaylistMenuItem.setActionCommand("EXPORT_PLAYLIST");
        exportPlaylistMenuItem.addActionListener(c);

        // Other components
        homeButton.setActionCommand("HOME");
        homeButton.addActionListener(c);
        searchButton.setActionCommand("SEARCH");
        searchButton.addActionListener(c);
        favoritesButton.setActionCommand("FAVORITES");
        favoritesButton.addActionListener(c);
        pausePlayButton.setActionCommand("PAUSE_PLAY");
        pausePlayButton.addActionListener(c);
        previousButton.setActionCommand("PREVIOUS");
        previousButton.addActionListener(c);
        nextButton.setActionCommand("NEXT");
        nextButton.addActionListener(c);
        randomButton.setActionCommand("RANDOM");
        randomButton.addActionListener(c);
        loopButton.setActionCommand("LOOP");
        loopButton.addActionListener(c);
        addToPlaylistButton.setActionCommand("ADD_TO_PLAYLIST");
        addToPlaylistButton.addActionListener(c);
        addToFavoritesButton.setActionCommand("ADD_TO_FAVORITES");
        addToFavoritesButton.addActionListener(c);
        songIconButton.setActionCommand("SONG_DETAILS");
        songIconButton.addActionListener(c);

        // Set the controller for each Panel
        playlistPanel.setController(c);
    }

    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        JFrameMainWindow window = new JFrameMainWindow();
    }
}
