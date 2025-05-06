package com.javaPlayer.project.view.GUI;

import com.formdev.flatlaf.themes.*;
import com.javaPlayer.project.controller.Controller;
import com.javaPlayer.project.controller.ControllerActions;
import com.javaPlayer.project.model.entity.*;
import com.javaPlayer.project.view.IViewMainWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class JFrameMainWindow extends JFrame implements IViewMainWindow {
    // Main panel
    private JPanel mainPanel;

    // Menu bar
    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenu editMenu;
    private final JMenu songMenu;
    private final JMenu playlistMenu;
    private final JMenuItem openSongMenuItem;
    private final JMenuItem accountMenuItem;
    private final JMenuItem settingsMenuItem;
    private final JMenuItem exitMenuItem;
    private final JMenuItem addSongToFavoritesMenuItem;
    private final JMenuItem removeSongFromFavoritesMenuItem;
    private final JMenuItem addSongToPlaylistMenuItem;
    private final JMenuItem removeSongFromPlaylistMenuItem;
    private final JMenuItem searchSongMenuItem;
    private final JMenuItem exportSongMenuItem;
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

    private Controller controller;

    public JFrameMainWindow() {
        // Set the window
        super("JavaPlayer - Playlist");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/app_icon.png"))).getImage());

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
        editMenu = new JMenu ("Edit");
        songMenu = new JMenu ("Song");
        playlistMenu = new JMenu ("Playlist");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(songMenu);
        menuBar.add(playlistMenu);

        // Set the menu items for the 'Files' menu
        openSongMenuItem = new JMenuItem("Open file");
        fileMenu.add(openSongMenuItem);

        // Set the menu items for the 'Edit' menu
        accountMenuItem = new JMenuItem("Switch account");
        settingsMenuItem = new JMenuItem("Settings");
        exitMenuItem = new JMenuItem("Exit");
        editMenu.add(accountMenuItem);
        editMenu.add(settingsMenuItem);
        editMenu.add(exitMenuItem);

        // Set the menu items for the 'Song' menu
        addSongToFavoritesMenuItem = new JMenuItem("Add to favorites");
        removeSongFromFavoritesMenuItem = new JMenuItem("Remove from favorites");
        addSongToPlaylistMenuItem = new JMenuItem("Add to playlist");
        removeSongFromPlaylistMenuItem = new JMenuItem("Remove from playlist");
        searchSongMenuItem = new JMenuItem("Search for a song");
        exportSongMenuItem = new JMenuItem("Export song");
        songMenu.add(addSongToFavoritesMenuItem);
        songMenu.add(removeSongFromFavoritesMenuItem);
        songMenu.add(addSongToPlaylistMenuItem);
        songMenu.add(removeSongFromPlaylistMenuItem);
        songMenu.add(searchSongMenuItem);
        songMenu.add(exportSongMenuItem);

        // Set the menu items for the 'Playlist' menu
        createPlaylistMenuItem = new JMenuItem("Create playlist");
        deletePlaylistMenuItem = new JMenuItem("Delete playlist");
        editPlaylistMenuItem = new JMenuItem("Edit playlist");
        playlistMenu.add(createPlaylistMenuItem);
        playlistMenu.add(deletePlaylistMenuItem);
        playlistMenu.add(editPlaylistMenuItem);

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
    public void updateSongPanel(String songTitle, String artistPseudo, byte[] songIcon, String duration, String elapsedTime, String remainingTime, boolean isSongFavorite) {
        songTitleLabel.setText(songTitle);
        songArtistLabel.setText(artistPseudo);

        if (songIcon != null) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(songIcon);
                BufferedImage bufferedImage = ImageIO.read(bais);
                songIconButton.setIcon(new ImageIcon(bufferedImage));
            } catch (Exception e) {
                throw new RuntimeException("Error while loading song icon : " + e.getMessage());
            }
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
    public void updateSongActionsPanel(boolean isRandom, boolean isPreviousSongPossible, boolean isLooping, boolean isPlaying) {
        if (isRandom) {
            randomButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/is_random_icon.png"))));
        } else {
            randomButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/is_not_random_icon.png"))));
        }

        if (isPreviousSongPossible) {
            previousButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/previous_possible_icon.png"))));
        } else {
            previousButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/previous_not_possible_icon.png"))));
        }

        if (isLooping) {
            loopButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/is_looping_icon.png"))));
        } else {
            loopButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/is_not_looping_icon.png"))));
        }

        if (isPlaying) {
            pausePlayButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/play_icon.png"))));
        } else {
            pausePlayButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/pause_icon.png"))));
        }
    }

    @Override
    public String getSelectedPlaylistTitle() {
        return homePanel.getSelectedPlaylistTitle();
    }

    @Override
    public void showHome() {
        cardLayout.show(contentPanel, "Home");
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
    public SongDetails showSongDetails(String title, String artist, String playlist, String addedDate, String duration) {
        JDialogSongDetails songDetailsDialog = new JDialogSongDetails(this,true);
        songDetailsDialog.updateSongDetails(title, artist, playlist, addedDate, duration);

        songDetailsDialog.setVisible(true);

        return new SongDetails(songDetailsDialog.getSongTitle(), songDetailsDialog.getSongArtist());
    }

    @Override
    public PlaylistSettings showAndGetPlaylistSettings(String playlistTitle, String playlistOwner, boolean canPlaylistBeRenamed, boolean canPlaylistBeDeleted) {
        JDialogPlaylistSettings playlistSettingsDialog = new JDialogPlaylistSettings(this, true, playlistTitle, playlistOwner, canPlaylistBeRenamed, canPlaylistBeDeleted);
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
    public void setController(Controller c) {
        // Set the controller
        controller = c;

        // Close window button action
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ControllerActions.EXIT_APP);
                controller.actionPerformed(event);
            }
        });

        // Menu items
        openSongMenuItem.setActionCommand(ControllerActions.OPEN_SONG);
        openSongMenuItem.addActionListener(c);
        accountMenuItem.setActionCommand(ControllerActions.SWITCH_ACCOUNT);
        accountMenuItem.addActionListener(c);
        settingsMenuItem.setActionCommand(ControllerActions.SETTINGS);
        settingsMenuItem.addActionListener(c);
        exitMenuItem.setActionCommand(ControllerActions.EXIT_APP);
        exitMenuItem.addActionListener(c);
        addSongToFavoritesMenuItem.setActionCommand(ControllerActions.ADD_TO_FAVORITES);
        addSongToFavoritesMenuItem.addActionListener(c);
        addSongToPlaylistMenuItem.setActionCommand(ControllerActions.ADD_TO_PLAYLIST);
        addSongToPlaylistMenuItem.addActionListener(c);
        removeSongFromFavoritesMenuItem.setActionCommand(ControllerActions.REMOVE_SONG_FROM_FAVORITES);
        removeSongFromFavoritesMenuItem.addActionListener(c);
        removeSongFromPlaylistMenuItem.setActionCommand(ControllerActions.REMOVE_SONG_FROM_PLAYLIST);
        removeSongFromPlaylistMenuItem.addActionListener(c);
        searchSongMenuItem.setActionCommand(ControllerActions.SEARCH_VIEW);
        searchSongMenuItem.addActionListener(c);
        exportSongMenuItem.setActionCommand(ControllerActions.EXPORT_SONG);
        exportSongMenuItem.addActionListener(c);
        createPlaylistMenuItem.setActionCommand(ControllerActions.CREATE_PLAYLIST);
        createPlaylistMenuItem.addActionListener(c);
        deletePlaylistMenuItem.setActionCommand(ControllerActions.DELETE_PLAYLIST);
        deletePlaylistMenuItem.addActionListener(c);
        editPlaylistMenuItem.setActionCommand(ControllerActions.PLAYLIST_SETTINGS);
        editPlaylistMenuItem.addActionListener(c);

        // Other components
        homeButton.setActionCommand(ControllerActions.HOME_VIEW);
        homeButton.addActionListener(c);
        searchButton.setActionCommand(ControllerActions.SEARCH_VIEW);
        searchButton.addActionListener(c);
        favoritesButton.setActionCommand(ControllerActions.FAVORITES_VIEW);
        favoritesButton.addActionListener(c);
        pausePlayButton.setActionCommand(ControllerActions.PAUSE_PLAY);
        pausePlayButton.addActionListener(c);
        previousButton.setActionCommand(ControllerActions.PREVIOUS);
        previousButton.addActionListener(c);
        nextButton.setActionCommand(ControllerActions.NEXT);
        nextButton.addActionListener(c);
        randomButton.setActionCommand(ControllerActions.RANDOM);
        randomButton.addActionListener(c);
        loopButton.setActionCommand(ControllerActions.LOOP);
        loopButton.addActionListener(c);
        addToPlaylistButton.setActionCommand(ControllerActions.ADD_TO_PLAYLIST);
        addToPlaylistButton.addActionListener(c);
        addToFavoritesButton.setActionCommand(ControllerActions.TOGGLE_FAVORITE);
        addToFavoritesButton.addActionListener(c);
        songIconButton.setActionCommand(ControllerActions.SONG_DETAILS);
        songIconButton.addActionListener(c);

        // Set the controller for each Panel
        homePanel.setController(c);
        searchPanel.setController(c);
        playlistPanel.setController(c);
    }

    @Override
    public File openFile(String fileType, String fileExtension) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open file");
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

    @Override
    public String saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save file");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            return fileToSave.getAbsolutePath();
        }

        return null;
    }

    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        JFrameMainWindow window = new JFrameMainWindow();
    }
}
