package com.javaPlayer.project.controller;

import com.javaPlayer.project.model.authentication.Authenticator;
import com.javaPlayer.project.model.dao.IDAOPlaylist;
import com.javaPlayer.project.model.dao.IDAOUser;
import com.javaPlayer.project.model.entity.*;
import com.javaPlayer.project.model.player.IMusicPlayer;
import com.javaPlayer.project.utils.Constants;
import com.javaPlayer.project.view.GUI.JFrameMainWindow;
import com.javaPlayer.project.view.IViewMainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.javaPlayer.project.utils.DurationFormatter.formatDuration;

public final class Controller implements ActionListener {
    private IViewMainWindow view;
    private Authenticator authenticator;
    private IDAOPlaylist daoPlaylist;
    private IDAOUser daoUser;
    private IMusicPlayer musicPlayer;

    // Current displayed data
    private String currentView;
    private Playlist currentPlaylist = null;
    private Song currentSong = null;
    private byte[] currentSongIcon = null;
    private boolean isCurrentSongFavorite;
    private boolean isCurrentSongChooserRandom = false;
    private boolean isCurrentSongLooping = false;
    private boolean isCurrentSongPlaying = false;
    private ArrayList<Song> searchResults = null;
    private Timer songTimer;

    public Controller(IViewMainWindow view, Authenticator authenticator, IDAOUser daoUser, IDAOPlaylist daoPlaylist, IMusicPlayer musicPlayer) {
        this.view = view;
        this.authenticator = authenticator;
        this.daoUser = daoUser;
        this.daoPlaylist = daoPlaylist;
        this.musicPlayer = musicPlayer;

        this.view.setController(this);
        this.musicPlayer.setController(this);

        this.daoUser.loadUsersFromFile();

        // Authenticate user
        switchUser(authenticate());

        // Update all playlist icons
        for (Playlist playlist : daoPlaylist.getPlaylistsList()) {
            updatePlaylistJacket(playlist);
        }

        // Update the current song view
        resetTime();
        updateSongPanel();
        resetTime();

        // Show home menu
        updateToHome();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case ControllerActions.HOME_VIEW: {
                updateToHome();

                break;
            }
            case ControllerActions.SEARCH_VIEW: {
                searchResults = daoPlaylist.getAllSongs();
                updateToSearch();

                break;
            }
            case ControllerActions.FAVORITES_VIEW: {
                currentPlaylist = daoPlaylist.getPlaylistByName(Constants.FAVORITES_PLAYLIST);
                updateToPlaylist();

                break;
            }
            case ControllerActions.PLAYLIST_VIEW: {
                // Retrieve the playlist selected by the user
                int playlistId = (int) e.getSource();
                Playlist selectedPlaylist = daoPlaylist.getPlaylistById(playlistId);

                if (selectedPlaylist != null) {
                    // Set the current song and play it
                    currentPlaylist = selectedPlaylist;
                    updateToPlaylist();
                } else {
                    view.showMessage("Playlist not found");
                }

                break;
            }
            case ControllerActions.SWITCH_ACCOUNT: {
                view.stop();

                if (currentSong != null) {
                    releaseCurrentSong();
                    currentSong = null;
                    updateSongPanel();
                    updateSongActionsPanel();
                    resetTime();
                }

                currentPlaylist = null;

                switchUser(authenticate());
                view.run();

                updateToHome();

                break;
            }
            case ControllerActions.SETTINGS: {
                showAndUpdateSettings();

                break;
            }
            case ControllerActions.SONG_DETAILS: {
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                showSongDetails();

                break;
            }
            case ControllerActions.PLAYLIST_SETTINGS: {
                if (currentPlaylist == null) {
                    view.showMessage("No playlist selected");
                    return;
                }

                showAndUpdatePlaylistSettings();

                break;
            }
            case ControllerActions.PAUSE_PLAY: {
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                if (isCurrentSongPlaying) {
                    pauseCurrentSong();
                } else {
                    resumeCurrentSong();
                }

                break;
            }
            case ControllerActions.PREVIOUS: {
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                if (currentPlaylist == null) {
                    view.showMessage("No playlist selected");
                    return;
                }

                releaseCurrentSong();
                selectPreviousSong();
                playCurrentSong();

                break;
            }
            case ControllerActions.NEXT: {
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                if (currentPlaylist == null) {
                    view.showMessage("No playlist selected");
                    return;
                }

                releaseCurrentSong();
                selectNextSong();
                playCurrentSong();

                break;
            }
            case ControllerActions.RANDOM: {
                toggleRandomSongChooser();

                break;
            }
            case ControllerActions.LOOP: {
                toggleSongLooping();

                break;
            }
            case ControllerActions.TOGGLE_FAVORITE: {
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                if (isCurrentSongFavorite) {
                    removeCurrentSongFromFavorites();
                } else {
                    addCurrentSongToFavorites();
                }

                break;
            }
            case ControllerActions.ADD_TO_FAVORITES: {
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                addCurrentSongToFavorites();

                break;
            }
            case ControllerActions.ADD_TO_PLAYLIST: {
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                addCurrentSongToPlaylist();

                break;
            }
            case ControllerActions.OPEN_SONG: {
                openSong();

                break;
            }
            case ControllerActions.EXPORT_SONG: {
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                exportSong();

                break;
            }
            case ControllerActions.REMOVE_SONG_FROM_FAVORITES: {
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                removeCurrentSongFromFavorites();
                updateSongPanel();

                break;
            }
            case ControllerActions.REMOVE_SONG_FROM_PLAYLIST: {
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                if (currentPlaylist == null) {
                    view.showMessage("No playlist selected");
                    return;
                }

                removeCurrentSongFromPlaylist();

                break;
            }
            case ControllerActions.DELETE_SONG: {
                deleteCurrentSong();

                break;
            }
            case ControllerActions.CREATE_PLAYLIST: {
                createPlaylist();

                break;
            }
            case ControllerActions.DELETE_PLAYLIST: {
                deletePlaylist();

                break;
            }
            case ControllerActions.EDIT_PLAYLIST: {
                editPlaylist();

                break;
            }
            case ControllerActions.SEARCH_SONG: {
                if (e.getSource() instanceof String) {
                    String wordToSearch = (String) e.getSource();
                    ArrayList<Song> filteredSong = new ArrayList<>();

                    for (Song song : searchResults) {
                        if (song.getTitle().toLowerCase().contains(wordToSearch.toLowerCase()) ||
                                song.getArtist().toLowerCase().contains(wordToSearch.toLowerCase())) {
                            filteredSong.add(song);
                        }
                    }

                    view.updateSearchPanel(filteredSong);
                }

                break;
            }
            case ControllerActions.PLAY_SELECTED_SONG: {
                // Retrieve the song selected by the user
                int songId = (int) e.getSource();
                Song selectedSong = daoPlaylist.getSongById(songId);

                if (selectedSong != null) {
                    // Set the current song and play it
                    currentSong = selectedSong;
                    currentSongIcon = musicPlayer.getSongIcon(currentSong.getFilename());
                    isCurrentSongFavorite = daoPlaylist.isSongInFavoritesPlaylist(currentSong);

                    playCurrentSong();
                } else {
                    view.showMessage("Song not found");
                }

                break;
            }
            case ControllerActions.CHOOSE_NEW_SONG: {
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                if (currentPlaylist == null) {
                    view.showMessage("No playlist selected");
                    return;
                }

                releaseCurrentSong();
                selectNewSong();
                playCurrentSong();

                break;
            }
            case ControllerActions.CHANGE_MUSIC_POSITION: {
                int selectedPosition = (int) e.getSource();

                if (currentSong != null) {
                    musicPlayer.seek(selectedPosition);
                }

                break;
            }
            case ControllerActions.EXIT_APP: {
                stop();
                clearResources();
                System.exit(0);

                break;
            }
            default: {
                view.showMessage("Button not implemented !");

                break;
            }
        }
    }

    // Run and stop view methods

    public void run() {
        view.run();
    }

    private void stop() {
        view.stop();
    }

    // User selection methods

    private User authenticate() {
        Credentials credentials;
        boolean isAuthenticated = false;
        do {
            // Show the account chooser dialog
            credentials = view.promptForCredentials();

            // If the user clicked on canceled or closed the dialog
            if (credentials.isCancellingRequest()) {
                stop();
                clearResources();
                System.exit(0);
            }

            // Create an account or log in to an existing account
            if (credentials.isCreatingAccount()) {
                if (authenticator.isLoginExists(credentials.getUsername())) {
                    view.showMessage("This pseudo already exists");
                } else {
                    authenticator.addUsers(credentials.getUsername(), credentials.getPassword());
                    daoUser.addUser(new User(credentials.getUsername(), credentials.getPassword()));
                    isAuthenticated = true;
                }
            } else {
                if (authenticator.isLoginExists(credentials.getUsername())) {
                    isAuthenticated = authenticator.authenticate(credentials.getUsername(), credentials.getPassword());

                    if (!isAuthenticated) {
                        view.showMessage("Password incorrect");
                    }
                } else {
                    view.showMessage("User doesn't exist");
                }
            }
        } while (!isAuthenticated);

        return daoUser.getUserByPseudo(credentials.getUsername());
    }

    private void switchUser(User user) {
        daoUser.setCurrentUser(user);
        daoPlaylist.loadPlaylistsConfigFile(user.getId());
        daoPlaylist.loadPlaylistsFromFile();
    }

    // Show view methods

    private void updateToHome() {
        view.updateHomePanel(daoPlaylist.getRecentPlaylistsList(Constants.RECENT_PLAYLIST_MINUTES), daoPlaylist.getPlaylistsList());
        view.showHome();
        currentView = Constants.HOME;
    }

    private void updateToSearch() {
        view.updateSearchPanel(searchResults);
        view.showSearch();
        currentView = Constants.SEARCH;
    }

    private void updateToPlaylist() {
        if (currentPlaylist == null) {
            return;
        }

        view.updatePlaylistPanel(currentPlaylist);
        view.showPlaylist();
        currentView = Constants.PLAYLIST;
    }

    private void updateSongPanel() {
        if (currentSong == null) {
            view.updateSongPanel("No song selected",
                    "",
                    null,
                    false);
        } else {
            view.updateSongPanel(currentSong.getTitle(),
                    currentSong.getArtist(),
                    currentSongIcon,
                    isCurrentSongFavorite);
        }
    }

    private void updateSongActionsPanel() {
        view.updateSongActionsPanel(isCurrentSongChooserRandom, !isCurrentSongChooserRandom, isCurrentSongLooping, isCurrentSongPlaying);
    }

    private void showAndUpdateSettings() {
        try {
            // Retrieve user pseudo and password
            Settings settings = view.showAndGetSettings(daoUser.getCurrentUser().getPseudo(), daoUser.getCurrentUser().getPassword());

            if (settings != null) {
                // If the user wants to delete all data
                if (settings.isDeletingAllData()) {
                    // Stop the app
                    stop();
                    clearResources();

                    // Delete playlists file
                    daoPlaylist.deleteAllCurrentUserData();
                    // Delete user from password file
                    authenticator.removeUser(daoUser.getCurrentUser().getPseudo());
                    // Delete user from users file
                    daoUser.removeUserById(daoUser.getCurrentUser().getId());

                    System.exit(0);
                }

                // If the user wants to change his pseudo or his password
                // Change users password file
                authenticator.changeUserPassword(daoUser.getCurrentUser().getPseudo(), settings.getUserPassword());
                authenticator.changeUserPseudo(daoUser.getCurrentUser().getPseudo(), settings.getUserPseudo());
                // Change users file
                daoUser.updateUserById(daoUser.getCurrentUser().getId(), settings.getUserPseudo(), settings.getUserPassword());
            }
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    private void showAndUpdatePlaylistSettings() {
        try {
            Playlist playlist = currentPlaylist; // Save the current playlist reference in case the user switch playlist

            PlaylistSettings playlistSettings = view.showAndGetPlaylistSettings(
                    playlist.getTitle(),
                    daoUser.getCurrentUser().getPseudo(),
                    daoPlaylist.canPlaylistBeRenamed(playlist.getTitle()),
                    daoPlaylist.canPlaylistBeDeleted(playlist.getTitle())
            );

            if (playlistSettings != null) {
                // If the user wants to delete the playlist
                if (playlistSettings.isDeletingPlaylist()) {
                    if (currentSong != null) {
                        if (daoPlaylist.getSongPlaylist(currentSong).equals(playlist)) {
                            releaseCurrentSong();
                            currentSong = null;
                            updateSongPanel();
                            resetTime();
                        }
                    }

                    daoPlaylist.removePlaylist(playlist);
                    daoPlaylist.savePlaylistsToFile();

                    // Update the UI
                    if ((currentView.equals(Constants.PLAYLIST)) || currentView.equals(Constants.HOME)) {
                        updateToHome();
                    }

                    if (currentPlaylist == playlist) {
                        currentPlaylist = null;
                    }

                    return;
                }

                // If the user wants to update the playlist
                if (!playlist.getTitle().equals(playlistSettings.getPlaylistTitle())) {
                    daoPlaylist.changePlaylistTitle(playlist.getTitle(), playlistSettings.getPlaylistTitle());
                    currentPlaylist = daoPlaylist.getPlaylistByName(playlistSettings.getPlaylistTitle());
                    daoPlaylist.savePlaylistsToFile();

                    // Update the UI
                    if (currentView.equals(Constants.PLAYLIST)) {
                        updateToPlaylist();
                    } else if (currentView.equals(Constants.HOME)) {
                        updateToHome();
                    }
                }
            }
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    private void showSongDetails() {
        Song song = currentSong;

        // Format the added date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = song.getAddedDate().format(formatter);

        // Ask to the view to display song details
        SongDetails songDetails = view.showSongDetails(song.getTitle(),
                song.getArtist(),
                daoPlaylist.getSongPlaylist(song).getTitle(),
                formattedDateTime,
                song.getFormattedDuration());

        // Update current song with the new information
        // Note : Need to call a method from the DAOPlaylist ?
        if (!song.getTitle().equals(songDetails.getSongTitle()) || !song.getArtist().equals(songDetails.getSongArtist())) {
            song.setTitle(songDetails.getSongTitle());
            song.setArtist(songDetails.getSongArtist());

            daoPlaylist.savePlaylistsToFile();

            // Update the UI
            updateSongPanel();

            if (currentView.equals(Constants.HOME)) {
                updateToHome();
            } else if (currentView.equals(Constants.PLAYLIST)) {
                updateToPlaylist();
            } else if (currentView.equals(Constants.SEARCH)) {
                updateToSearch();
            }
        }
    }

    // Music control methods

    public void playCurrentSong() {
        File file = new File(currentSong.getFilename());

        if (file.exists()) {
            musicPlayer.loadAndPlay(currentSong.getFilename());
            startSongTimer();
            isCurrentSongPlaying = true;

            // Update the Last viewed date of the playlist
            daoPlaylist.getSongPlaylist(currentSong).setLastViewedDate(LocalDateTime.now());

            // Update the UI
            updateSongActionsPanel();
        } else {
            view.showMessage("Music file not found");
        }
    }

    public void pauseCurrentSong() {
        // Pause the current song playing with the Music player
        if (isCurrentSongPlaying) {
            musicPlayer.pause();
            isCurrentSongPlaying = false;
            if (songTimer != null) songTimer.stop();

            // Update the UI
            updateSongActionsPanel();
        }
    }

    public void resumeCurrentSong() {
        if (!isCurrentSongPlaying) {
            musicPlayer.resume();
            startSongTimer();
            isCurrentSongPlaying = true;

            // Update the UI
            updateSongActionsPanel();
        }
    }

    public void releaseCurrentSong() {
        if (songTimer != null) songTimer.stop();
        musicPlayer.release();
        isCurrentSongPlaying = false;

        // Update the UI
        updateSongActionsPanel();
    }

    private void startSongTimer() {
        if (songTimer != null) {
            songTimer.stop();
        }

        // Start a timer to update the song panel every second
        songTimer = new Timer(1000, e -> {
            long total = musicPlayer.getTotalDuration();
            long current = musicPlayer.getCurrentPosition();

            // Update the UI
            view.updateTime((int) current, (int) total, formatDuration(Duration.ofMillis(current)), formatDuration(Duration.ofMillis(total - current)));
            updateSongPanel();
        });
        songTimer.start();
    }

    private void resetTime() {
        view.updateTime(0, 0, formatDuration(Duration.ofMillis(0)), formatDuration(Duration.ofMillis(0)));
    }

    private void toggleRandomSongChooser() {
        isCurrentSongChooserRandom = !isCurrentSongChooserRandom;

        // Update the UI
        updateSongActionsPanel();
    }

    private void toggleSongLooping() {
        isCurrentSongLooping = !isCurrentSongLooping;

        // Update the UI
        updateSongActionsPanel();
    }

    private void selectPreviousSong() {
        // If the current mode is random
        if (isCurrentSongChooserRandom) {
            return;
        }

        // Select the previous song
        currentSong = daoPlaylist.getPreviousSong(currentSong, currentPlaylist);

        // Update the UI
        updateSongPanel();
    }

    private void selectNextSong() {
        Playlist currentPlaylist = daoPlaylist.getSongPlaylist(currentSong);

        if (isCurrentSongChooserRandom) {
            currentSong = daoPlaylist.getRandomSong(currentPlaylist);
        } else {
            currentSong = daoPlaylist.getNextSong(currentSong, currentPlaylist);
        }

        // Update the UI
        updateSongPanel();
    }

    private void selectNewSong() {
        // If the current song is not looping
        if (!isCurrentSongLooping) {
            selectNextSong();
        }
    }

    // Current song methods

    private void addCurrentSongToFavorites() {
        try {
            Playlist oldPlaylist = daoPlaylist.getSongPlaylist(currentSong);

            if (oldPlaylist.getTitle().equals(Constants.FAVORITES_PLAYLIST)) {
                view.showMessage("Song is already in Favorites playlist");
                return;
            }

            // Remove current song from the current playlist
            daoPlaylist.changeSongPlaylist(currentSong, daoPlaylist.getPlaylistByName(Constants.FAVORITES_PLAYLIST));

            // Update playlist icons
            updatePlaylistJacket(oldPlaylist);
            updatePlaylistJacket(daoPlaylist.getPlaylistByName(Constants.FAVORITES_PLAYLIST));

            daoPlaylist.savePlaylistsToFile();

            // Update current song flag
            isCurrentSongFavorite = true;

            // Update the UI
            if (currentView.equals(Constants.PLAYLIST) && (currentPlaylist.equals(oldPlaylist) || currentPlaylist.getTitle().equals(Constants.FAVORITES_PLAYLIST))) {
                updateToPlaylist();
            }

            updateSongPanel();
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    private void removeCurrentSongFromFavorites() {
        try {
            Playlist oldPlaylist = daoPlaylist.getSongPlaylist(currentSong);

            if (!oldPlaylist.getTitle().equals(Constants.FAVORITES_PLAYLIST)) {
                view.showMessage("Song is not in Favorites playlist");
                return;
            }

            // Move current song to "Unclassed songs" playlist
            daoPlaylist.changeSongPlaylist(currentSong, daoPlaylist.getPlaylistByName(Constants.UNCLASSED_SONGS_PLAYLIST));

            // Update playlist icons
            updatePlaylistJacket(oldPlaylist);
            updatePlaylistJacket(daoPlaylist.getPlaylistByName(Constants.UNCLASSED_SONGS_PLAYLIST));

            daoPlaylist.savePlaylistsToFile();

            // Update current song flag
            isCurrentSongFavorite = false;

            // Update the UI
            if (currentView.equals(Constants.PLAYLIST) && (currentPlaylist.getTitle().equals(Constants.FAVORITES_PLAYLIST) || currentPlaylist.getTitle().equals(Constants.UNCLASSED_SONGS_PLAYLIST))) {
                updateToPlaylist();
            }

            updateSongPanel();
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    private void addCurrentSongToPlaylist() {
        try {
            Playlist oldPlaylist = daoPlaylist.getSongPlaylist(currentSong);
            ArrayList<Playlist> basePlaylistsList = daoPlaylist.getBasePlaylistsList();

            if (basePlaylistsList.isEmpty()) {
                view.showMessage("No playlists to add the song to");
                return;
            }

            String selectedPlaylist = view.promptChooseAddToPlaylist(basePlaylistsList);

            if (selectedPlaylist != null) {
                daoPlaylist.changeSongPlaylist(currentSong, daoPlaylist.getPlaylistByName(selectedPlaylist));
                daoPlaylist.savePlaylistsToFile();

                // Update playlist icons
                updatePlaylistJacket(oldPlaylist);
                updatePlaylistJacket(daoPlaylist.getPlaylistByName(selectedPlaylist));

                daoPlaylist.savePlaylistsToFile();

                // Update the UI
                if (currentView.equals(Constants.PLAYLIST)) {
                    updateToPlaylist();
                }
            }
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    private void removeCurrentSongFromPlaylist() {
        try {
            Playlist oldPlaylist = daoPlaylist.getSongPlaylist(currentSong);
            Playlist newPlaylist = daoPlaylist.getPlaylistByName(Constants.UNCLASSED_SONGS_PLAYLIST);
            daoPlaylist.changeSongPlaylist(currentSong, newPlaylist);

            updatePlaylistJacket(oldPlaylist);
            updatePlaylistJacket(newPlaylist);

            daoPlaylist.savePlaylistsToFile();

            // Update the UI
            if (currentView.equals(Constants.PLAYLIST)) {
                updateToPlaylist();
            }

            view.showMessage("Song removed from playlist and placed in Unclassed songs playlist");
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    private void deleteCurrentSong() {
        if (currentSong == null) {
            view.showMessage("No song selected");
            return;
        }

        Playlist songPlaylist = daoPlaylist.getSongPlaylist(currentSong);

        if (songPlaylist != null) {
            Song oldFirstSong = songPlaylist.getSongList().getFirst();

            releaseCurrentSong();
            daoPlaylist.deleteSongFromPlaylist(songPlaylist, currentSong);

            if (oldFirstSong.equals(currentSong)) {
                updatePlaylistJacket(songPlaylist);
            }

            currentSong = null;
            currentSongIcon = null;

            daoPlaylist.savePlaylistsToFile();

            if (currentView.equals(Constants.PLAYLIST) && currentPlaylist.equals(songPlaylist)) {
                updateToPlaylist();
            }

            updateSongPanel();
            resetTime();
        } else {
            view.showMessage("Song not found in any playlist");
        }
    }

    // Playlists methods

    private void createPlaylist() {
        try {
            // Ask for the new playlist name
            String playlistName = view.promptToCreatePlaylist();

            if (playlistName != null) {
                // Create a new playlist
                daoPlaylist.createPlaylist(playlistName);
                updatePlaylistJacket(daoPlaylist.getPlaylistByName(playlistName));

                daoPlaylist.savePlaylistsToFile();

                // Update the UI
                currentPlaylist = daoPlaylist.getPlaylistByName(playlistName);
                updateToPlaylist();
            }
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    private void deletePlaylist() {
        try {
            ArrayList<Playlist> basePlaylistsList = daoPlaylist.getBasePlaylistsList();

            if (basePlaylistsList.isEmpty()) {
                view.showMessage("No playlists to delete");
                return;
            }

            String playlistToDelete = view.promptChoosePlaylistToDelete(basePlaylistsList);

            if (playlistToDelete != null) {
                if (currentSong != null) {
                    if (daoPlaylist.getSongPlaylist(currentSong).getTitle().equals(playlistToDelete)) {
                        releaseCurrentSong();
                        currentSong = null;
                        updateSongPanel();
                        resetTime();
                    }
                }

                // Delete playlist
                daoPlaylist.removePlaylist(daoPlaylist.getPlaylistByName(playlistToDelete));
                daoPlaylist.savePlaylistsToFile();

                // Update currentPlaylist and the UI if necessary
                if (currentPlaylist != null && currentPlaylist.getTitle().equals(playlistToDelete)) {
                    currentPlaylist = null;
                    updateToHome();
                } else if (currentView.equals(Constants.HOME)) {
                    updateToHome();
                }
            }
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    private void editPlaylist() {
        try {
            ArrayList<Playlist> basePlaylistsList = daoPlaylist.getBasePlaylistsList();

            if (basePlaylistsList.isEmpty()) {
                view.showMessage("No playlists to edit");
                return;
            }

            String playlistName = view.promptChoosePlaylistToEdit(basePlaylistsList);

            if (playlistName != null) {
                Playlist playlistToEdit = daoPlaylist.getPlaylistByName(playlistName);

                if (playlistToEdit != null) {
                    currentPlaylist = playlistToEdit;
                    showAndUpdatePlaylistSettings();
                }
            }
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    // Import and export methods

    private void openSong() {
        // Ask the user to select songs
        File[] songFile = view.openFile("Audio files (*.mp3, *.m4a)", "mp3", "m4a");

        if (songFile != null) {
            currentPlaylist = daoPlaylist.getPlaylistByName(Constants.UNCLASSED_SONGS_PLAYLIST);

            // Add each song to the "Unclassed songs" playlist
            for (File addSong : songFile) {
                SongMetadata currentSongMetadata = musicPlayer.getSongMetadata(addSong.getAbsolutePath());
                currentSong = new Song(
                        currentSongMetadata.getTitle(),
                        currentSongMetadata.getArtist(),
                        currentSongMetadata.getGenre(),
                        currentSongMetadata.getDuration(),
                        LocalDateTime.now(),
                        addSong.getAbsolutePath()
                );

                daoPlaylist.importSongToPlaylist(currentPlaylist, currentSong);
            }

            updatePlaylistJacket(currentPlaylist);
            daoPlaylist.savePlaylistsToFile();
            updateToPlaylist();
        }
    }

    private void exportSong() {
        try {
            String newFilename = view.saveFile(currentSong.getTitle(), currentSong.getSongFileExtension(), "Audio files (*.mp3, *.m4a)", "mp3", "m4a");

            if (newFilename != null) {
                daoPlaylist.exportSong(currentSong, newFilename);

                view.showMessage("Song exported to : " + newFilename);
            }
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    // Utils methods

    private void clearResources() {
        musicPlayer.stop();
        musicPlayer.clearResources();
        view.release();
    }

    private void updatePlaylistJacket(Playlist playlist) {
        byte[] playlistJacket;

        if (!playlist.getSongList().isEmpty()) {
            playlistJacket = musicPlayer.getSongIcon(playlist.getSongList().getFirst().getFilename());
            playlist.setIcon(playlistJacket);
            // Note : Use a method from the DAOPlaylist ?
        } else {
            playlist.setIcon(null);
        }
    }
}
