package com.javaPlayer.project.view.GUI;

import com.formdev.flatlaf.themes.*;
import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.controller.MainControllerActions;
import com.javaPlayer.project.model.entity.*;
import com.javaPlayer.project.view.ViewMainWindow;
import org.jaudiotagger.tag.datatype.Artwork;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

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
    private final JMenuItem accountMenuItem;
    private final JMenuItem settingsMenuItem;
    private final JMenuItem addSongToFavoritesMenuItem;
    private final JMenuItem removeSongFromFavoritesMenuItem;
    private final JMenuItem addSongToPlaylistMenuItem;
    private final JMenuItem removeSongFromPlaylistMenuItem;
    private final JMenuItem searchSongMenuItem;
    private final JMenuItem createPlaylistMenuItem;
    private final JMenuItem deletePlaylistMenuItem;
    private final JMenuItem editPlaylistMenuItem;
    private final JMenuItem importPlaylistMenuItem;
    private final JMenuItem exportPlaylistMenuItem;


    // Left Menu Panel
    private JButton homeButton;
    private JButton searchButton;
    private JButton favoritesButton;
    private JPanel leftMenuPanel;

    // Song panel
    private JPanel songPanel;
//    private JScrollPane contentScrollPane;
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
    private JPanel recentPlaylistsButtonsPanel;

    // Add the different content panels
    private JPanelHome homePanel = new JPanelHome();
    private JPanelPlaylist playlistPanel = new JPanelPlaylist();
    private JPanelSearch searchPanel = new JPanelSearch();
    private boolean isSongFavorite;

    private MainController controller;
//    private User user;

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
        accountMenuItem = new JMenuItem("Switch account");
        settingsMenuItem = new JMenuItem("Settings");
        editMenu.add(accountMenuItem);
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
        importPlaylistMenuItem = new JMenuItem("Import playlist");
        exportPlaylistMenuItem = new JMenuItem("Export playlist");
        playlistMenu.add(createPlaylistMenuItem);
        playlistMenu.add(deletePlaylistMenuItem);
        playlistMenu.add(editPlaylistMenuItem);
        playlistMenu.add(importPlaylistMenuItem);
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
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add the main content
        contentPanel.add(homePanel.mainPanel, "Home");
        contentPanel.add(playlistPanel.mainPanel, "Playlist");
        contentPanel.add(searchPanel.mainPanel, "Search");

        cardLayout.show(contentPanel, "Home");
    }

    @Override
    public Credentials promptForCredentials() {
        Credentials credentials;
        boolean isCredentialEmpty = false;

        do {
            JDialogAccountChooser accountChooserDialog = new JDialogAccountChooser();
            accountChooserDialog.setTitle("Login or create an account");
            accountChooserDialog.setModal(true);
            accountChooserDialog.setLocationRelativeTo(null);
            accountChooserDialog.setVisible(true);

            credentials = new Credentials(accountChooserDialog.isCancelled(), accountChooserDialog.isCreatingAccount(), accountChooserDialog.getPseudo(), accountChooserDialog.getPassword());

            if (!accountChooserDialog.isCancelled() && (accountChooserDialog.getPassword().isEmpty() || accountChooserDialog.getPassword().isEmpty())) {
                showMessage("Pseudo or password cannot be empty.");
                isCredentialEmpty = true;
            }
            else {
                isCredentialEmpty = false;
                accountChooserDialog.dispose();
            }
        } while (isCredentialEmpty);

        return credentials;
    }

    @Override
    public void updateHomePanel(ArrayList<Playlist> recentPlaylistsList, ArrayList<Playlist> allPlaylistsList) {
        homePanel.updateRecentPlaylists(recentPlaylistsList);
        homePanel.updateAllPlaylists(allPlaylistsList);
    }

    @Override
    public void updatePlaylistPanel(Playlist playlist) {
        playlistPanel.updatePlaylist(playlist);
    }

    @Override
    public void updateSearchPanel(ArrayList<Song> songList) {
        searchPanel.updateResults(songList);
    }

    @Override
    public void updateSongPanel(String songTitle, String artistPseudo, Icon songIcon, String duration, String elapsedTime, String remainingTime, boolean isSongFavorite) {
        songTitleLabel.setText(songTitle);
        songArtistLabel.setText(artistPseudo);

        if (songIcon != null) {
            songIconButton.setIcon(songIcon);
        } else {
            songIconButton.setText(songTitle.substring(0, 1));
        }

        if (isSongFavorite) {
            addToFavoritesButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/is_favorite_song_icon.png"))));
        } else {
            addToFavoritesButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/is_not_favorite_song_icon.png"))));
        }
    }

    @Override
    public String getSelectedPlaylistTitle() {
        return homePanel.getSelectedPlaylistTitle();
    }

    @Override
    public void showHome() {
        cardLayout.show(contentPanel, "Home"); // Note : Do it need to be destroyed and re-created ?
    }

    @Override
    public void showSearch() {
        cardLayout.show(contentPanel, "Search");
    }

    @Override
    public void showPlaylist() {
        cardLayout.show(contentPanel, "Playlist");
    }

    public JDialogAccountChooser showAccountChooserDialog() {
        JDialogAccountChooser accountChooserDialog = new JDialogAccountChooser();
        accountChooserDialog.setTitle("Login or create an account");
        accountChooserDialog.setModal(true);
        accountChooserDialog.setLocationRelativeTo(null);
        accountChooserDialog.setVisible(true);

        return accountChooserDialog;
    }

    @Override
    public Settings showAndGetSettings(String userPseudo, String userPassword) {
        JDialogSettings settingsDialog = new JDialogSettings(this,true, userPseudo, userPassword);
        settingsDialog.setVisible(true);

        if (settingsDialog.isSaving()) {
            return new Settings(settingsDialog.getUserPseudo(),
                    settingsDialog.getUserPassword(),
                    settingsDialog.isDeletingAllData());
        } else {
            return null;
        }
    }

    @Override
    public void showSongDetails(String title, String artist, String playlist, String addedDate, String duration) {
        JDialogSongDetails songDetailsDialog = new JDialogSongDetails(this,true);
        if (title != null) songDetailsDialog.setSongTitle(title);
        if (artist != null) songDetailsDialog.setSongArtist(artist);
        if (playlist != null) songDetailsDialog.setSongPlaylist(playlist);
        if (addedDate != null) songDetailsDialog.setSongAddedDate(addedDate);
        if (duration != null) {
            songDetailsDialog.setSongDuration(duration);
        } else {
            // Note : To remove
            System.out.println("Song duration is null");
        };

        songDetailsDialog.setVisible(true);
    }

    @Override
    public PlaylistSettings showAndGetPlaylistSettings(String playlistTitle, String playlistOwner)
    {
        JDialogPlaylistSettings playlistSettingsDialog = new JDialogPlaylistSettings(this, true, playlistTitle, playlistOwner);
        playlistSettingsDialog.setVisible(true);

        if (playlistSettingsDialog.isSaving()) {
            return new PlaylistSettings(playlistSettingsDialog.getPlaylistName(), playlistSettingsDialog.isDeletingPlaylist());
        }
        else {
            return null;
        }
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public String promptChooseAddToPlaylist(ArrayList<String> playlistTitleList) {
        JDialogAddToPlaylist addToPlaylistDialog = new JDialogAddToPlaylist(this, true, playlistTitleList);
        addToPlaylistDialog.setVisible(true);

        if (addToPlaylistDialog.isAddingSongToPlaylist()) {
            return addToPlaylistDialog.getSelectedPlaylist();
        } else {
            return null;
        }
    }

    @Override
    public String promptToCreatePlaylist() {
        JDialogCreatePlaylist createPlaylistDialog = new JDialogCreatePlaylist(this, true);
        createPlaylistDialog.setVisible(true);

        if (createPlaylistDialog.isCreatingPlaylist()) {
            return createPlaylistDialog.getNewPlaylistName();
        } else {
            return null;
        }
    }

    @Override
    public String promptChoosePlaylistToDelete(ArrayList<String> playlistTitleList) {
        JDialogDeletePlaylist playlistToDeleteDialog = new JDialogDeletePlaylist(this, true, playlistTitleList);
        playlistToDeleteDialog.setVisible(true);

        return playlistToDeleteDialog.getSelectedPlaylist();
    }

    @Override
    public void run() {
        this.setVisible(true);
    }

    @Override
    public void stop() {
        this.setVisible(false);
    }

    @Override
    public void setController(MainController c) {
        // Menu items
        openSongMenuItem.setActionCommand(MainControllerActions.OPEN_SONG);
        openSongMenuItem.addActionListener(c);
        accountMenuItem.setActionCommand(MainControllerActions.SWITCH_ACCOUNT);
        accountMenuItem.addActionListener(c);
        settingsMenuItem.setActionCommand(MainControllerActions.SETTINGS);
        settingsMenuItem.addActionListener(c);
        createBackupMenuItem.setActionCommand(MainControllerActions.CREATE_BACKUP);
        createBackupMenuItem.addActionListener(c);
        addSongToFavoritesMenuItem.setActionCommand(MainControllerActions.ADD_TO_FAVORITES);
        addSongToFavoritesMenuItem.addActionListener(c);
        addSongToPlaylistMenuItem.setActionCommand(MainControllerActions.ADD_TO_PLAYLIST);
        addSongToPlaylistMenuItem.addActionListener(c);
        removeSongFromFavoritesMenuItem.setActionCommand(MainControllerActions.REMOVE_SONG_FROM_FAVORITES);
        removeSongFromFavoritesMenuItem.addActionListener(c);
        removeSongFromPlaylistMenuItem.setActionCommand(MainControllerActions.REMOVE_SONG_FROM_PLAYLIST);
        removeSongFromPlaylistMenuItem.addActionListener(c);
        searchSongMenuItem.setActionCommand(MainControllerActions.SEARCH_VIEW);
        searchSongMenuItem.addActionListener(c);
        createPlaylistMenuItem.setActionCommand(MainControllerActions.CREATE_PLAYLIST);
        createPlaylistMenuItem.addActionListener(c);
        deletePlaylistMenuItem.setActionCommand(MainControllerActions.DELETE_PLAYLIST);
        deletePlaylistMenuItem.addActionListener(c);
        editPlaylistMenuItem.setActionCommand(MainControllerActions.PLAYLIST_SETTINGS);
        editPlaylistMenuItem.addActionListener(c);
        importPlaylistMenuItem.setActionCommand(MainControllerActions.IMPORT_PLAYLIST);
        importPlaylistMenuItem.addActionListener(c);
        exportPlaylistMenuItem.setActionCommand(MainControllerActions.EXPORT_PLAYLIST);
        exportPlaylistMenuItem.addActionListener(c);

        // Other components
        homeButton.setActionCommand(MainControllerActions.HOME_VIEW);
        homeButton.addActionListener(c);
        searchButton.setActionCommand(MainControllerActions.SEARCH_VIEW);
        searchButton.addActionListener(c);
        favoritesButton.setActionCommand(MainControllerActions.FAVORITES_VIEW);
        favoritesButton.addActionListener(c);
        pausePlayButton.setActionCommand(MainControllerActions.PAUSE_PLAY);
        pausePlayButton.addActionListener(c);
        previousButton.setActionCommand(MainControllerActions.PREVIOUS);
        previousButton.addActionListener(c);
        nextButton.setActionCommand(MainControllerActions.NEXT);
        nextButton.addActionListener(c);
        randomButton.setActionCommand(MainControllerActions.RANDOM);
        randomButton.addActionListener(c);
        loopButton.setActionCommand(MainControllerActions.LOOP);
        loopButton.addActionListener(c);
        addToPlaylistButton.setActionCommand(MainControllerActions.ADD_TO_PLAYLIST);
        addToPlaylistButton.addActionListener(c);
        addToFavoritesButton.setActionCommand(MainControllerActions.TOGGLE_FAVORITE);
        addToFavoritesButton.addActionListener(c);
        songIconButton.setActionCommand(MainControllerActions.SONG_DETAILS);
        songIconButton.addActionListener(c);

        // Set the controller for each Panel
        homePanel.setController(c);
        searchPanel.setController(c);
        playlistPanel.setController(c);
    }

    @Override
    public File openFile(String fileType, String fileExtension) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType, fileExtension);
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null); // Note : Does parent could be "this" ?

        File currentFile;

        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
        } else {
            currentFile = null;
        }

        return currentFile;
    }

    private void updateSongData(String title, String artist, Artwork albumImage, boolean isSongFavorite) {
        songTitleLabel.setText(title);
        songArtistLabel.setText(artist);

        if (albumImage != null) {
            byte[] imageData = albumImage.getBinaryData();
            ImageIcon originalIcon = new ImageIcon(imageData);
            Image scaledImage = originalIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Note : Size need to be changer

            songIconButton.setIcon(new ImageIcon(scaledImage));
        } else {
            songIconButton.setText(title.substring(0, 1));
        }

        if (isSongFavorite) {
            addToFavoritesButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/is_favorite_song_icon.png"))));
        } else {
            addToFavoritesButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/favorite_song_icon.png"))));
        }
    }

    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        JFrameMainWindow window = new JFrameMainWindow();
    }
}
