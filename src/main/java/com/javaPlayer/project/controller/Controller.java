package com.javaPlayer.project.controller;

import com.javaPlayer.project.model.authentication.Authenticator;
import com.javaPlayer.project.model.dao.DAOPlaylist;
import com.javaPlayer.project.model.dao.DAOUser;
import com.javaPlayer.project.model.dao.IDAOPlaylist;
import com.javaPlayer.project.model.dao.IDAOUser;
import com.javaPlayer.project.model.entity.*;
import com.javaPlayer.project.model.player.IMusicPlayer;
import com.javaPlayer.project.utils.Constants;
import com.javaPlayer.project.view.GUI.JFrameMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.javaPlayer.project.utils.DurationFormatter.formatDuration;

public final class Controller implements ActionListener {
    private JFrameMainWindow view;
    private Authenticator authenticator;
    private IDAOPlaylist daoPlaylist;
    private IDAOUser daoUser;
    private IMusicPlayer musicPlayer;

    // Current displayed data
    private String currentView;
    private Playlist currentPlaylist = null;
    private Song currentSong = null;
    private byte[] currentSongIcon = null;
    private User currentUser = null;
    private boolean isCurrentSongFavorite;
    private boolean isCurrentSongChooserRandom = false;
    private boolean isCurrentSongLooping = false;
    private boolean isCurrentSongPlaying = false;
    private ArrayList<Song> searchResults = null;

    // Note : Not used yet
    private Duration remainingSongDuration;
    private Duration elapsedSongDuration;

    public Controller(JFrameMainWindow view, Authenticator authenticator, IDAOUser daoUser, IDAOPlaylist daoPlaylist, IMusicPlayer musicPlayer) {
        this.view = view;
        this.authenticator = authenticator;
        this.daoUser = daoUser;
        this.daoPlaylist = daoPlaylist;
        this.musicPlayer = musicPlayer;

        this.view.setController(this);
        this.daoUser.loadUsersFromFile();

        // Authenticate user
        switchUser(authenticate());
        view.setVisible(true);

        // Set up current song
//        currentSong = daoPlaylist.getLastPlayedSong();
//
//        if (currentSong == null) {
//            currentSong = daoPlaylist.getFirstSong();
//        }
//
//        if (currentSong != null) {
//            currentPlaylist = daoPlaylist.getSongPlaylist(currentSong);
//            currentSongIcon = musicPlayer.getSongIcon(currentSong.getFilename());
//            isCurrentSongFavorite = daoPlaylist.isSongInFavoritesPlaylist(currentSong);
//            playCurrentSong();
//            pauseCurrentSong();
//        }

        updateSongPanel();
        updateSongActionsPanel();

        // Show home menu
        view.updateHomePanel(daoPlaylist.getRecentPlaylistsList(Constants.RECENT_PLAYLIST_MINUTES), daoPlaylist.getPlaylistsList());
        view.showHome();
        currentView = Constants.HOME;
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
                currentPlaylist = daoPlaylist.getPlaylistByName("Favorites");
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
                switchUser(authenticate());
                view.run();

                updateToHome();

                break;
            }
            case ControllerActions.SETTINGS: {
                openAndUpdateSettings();
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
                // If there is no song selected
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                // If there is no playlist selected
                if (currentPlaylist == null) {
                    view.showMessage("No playlist selected");
                    return;
                }

                selectPreviousSong();
                playCurrentSong();

                break;
            }
            case ControllerActions.NEXT: {
                // If there is no song selected
                if (currentSong == null) {
                    view.showMessage("No song selected");
                    return;
                }

                // If there is no playlist selected
                if (currentPlaylist == null) {
                    view.showMessage("No playlist selected");
                    return;
                }

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

                if (currentPlaylist == null) {
                    view.showMessage("No playlist selected");
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
                String newFilename = view.saveFile();

                if (newFilename != null) {
                    daoPlaylist.exportSong(currentSong, newFilename);
                }

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

//                    daoPlaylist.setLastPlayedSong(selectedSong);
                    playCurrentSong();

                    // Update the UI
                    updateSongPanel();
                } else {
                    view.showMessage("Song not found");
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

    public User authenticate() {
        Credentials credentials;
        boolean isAuthenticated = false;
        do {
            // Show the account chooser dialog
            credentials = view.promptForCredentials();

            // Clicked on 'Cancel' or closed the dialog
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
                    daoUser.saveUsersToFile();
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

    public void switchUser(User user) {
        daoUser.setCurrentUser(user);
        daoPlaylist.loadPlaylistsConfigFile(user.getId());
        daoPlaylist.loadPlaylistsFromFile();
    }

    public void run() {
        view.run();
    }

    public void stop() {
        view.stop();
    }

    public void clearResources() {
        musicPlayer.release();
        view.dispose();
    }

    public void updateToHome() {
        view.updateHomePanel(daoPlaylist.getRecentPlaylistsList(Constants.RECENT_PLAYLIST_MINUTES), daoPlaylist.getPlaylistsList());
        view.showHome();
        currentView = Constants.HOME;
    }

    public void updateToSearch() {
        view.updateSearchPanel(searchResults);
        view.showSearch();
        currentView = Constants.SEARCH;
    }

    public void updateToPlaylist() {
        if (currentPlaylist == null) {
            return;
        }

        view.updatePlaylistPanel(currentPlaylist);
        view.showPlaylist();
        currentView = Constants.PLAYLIST;
    }

    public void openAndUpdateSettings() {
        try {
            // Retrieve user pseudo and password
            Settings settings = view.showAndGetSettings(daoUser.getCurrentUser().getPseudo(), daoUser.getCurrentUser().getPassword());

            if (settings != null) {
                // If the user wants to delete all data
                if (settings.isDeletingAllData()) {
                    clearResources();
                    // Delete playlists file
                    daoPlaylist.deleteAllCurrentUserData();
                    // Delete user from password file
                    authenticator.removeUser(daoUser.getCurrentUser().getPseudo());
                    // Delete user from users file
                    daoUser.removeUserById(daoUser.getCurrentUser().getId());
                    System.exit(0);
                }

                // If the user wants to change his pseudo or password
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

    public void showSongDetails() {
        // Format the added date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentSong.getAddedDate().format(formatter);

        // Ask to the view to display song details
        SongDetails songDetails = view.showSongDetails(currentSong.getTitle(),
                currentSong.getArtist(),
                daoPlaylist.getSongPlaylist(currentSong).getTitle(),
                formattedDateTime,
                currentSong.getFormattedDuration());

        // Update current song with new information
        currentSong.setTitle(songDetails.getSongTitle());
        currentSong.setArtist(songDetails.getSongArtist());

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

    public void showAndUpdatePlaylistSettings() {
        try {
            PlaylistSettings playlistSettings = view.showAndGetPlaylistSettings(
                    currentPlaylist.getTitle(),
                    daoUser.getCurrentUser().getPseudo(),
                    daoPlaylist.canPlaylistBeRenamed(currentPlaylist.getTitle()),
                    daoPlaylist.canPlaylistBeDeleted(currentPlaylist.getTitle())
            );

            if (playlistSettings != null) {
                // If the user wants to delete the playlist
                if (playlistSettings.isDeletingPlaylist()) {
                    daoPlaylist.removePlaylist(currentPlaylist);
                    daoPlaylist.savePlaylistsToFile();

                    // Update the UI
                    if ((currentView.equals(Constants.PLAYLIST)) || currentView.equals(Constants.HOME)) {
                        updateToHome();
                    }

                    currentPlaylist = null;

                    return;
                }

                // If the user wants to update the playlist
                if (!currentPlaylist.getTitle().equals(playlistSettings.getPlaylistTitle())) {
                    daoPlaylist.changePlaylistTitle(currentPlaylist.getTitle(), playlistSettings.getPlaylistTitle());
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

    public void updateSongPanel() {
        if (currentSong == null) {
            view.updateSongPanel("No song selected",
                    "",
                    null,
                    "",
                    "00:00",
                    "00:00",
                    false);
        } else {
            view.updateSongPanel(currentSong.getTitle(),
                    currentSong.getArtist(),
                    currentSongIcon,
                    currentSong.getFormattedDuration(),
                    formatDuration(remainingSongDuration),
                    formatDuration(elapsedSongDuration),
                    isCurrentSongFavorite);
        }
    }

    public void updateSongActionsPanel() {
        view.updateSongActionsPanel(isCurrentSongChooserRandom, !isCurrentSongChooserRandom, isCurrentSongLooping, isCurrentSongPlaying);
    }

    public void playCurrentSong() {
        File file = new File(currentSong.getFilename());

        if (file.exists()) {
            musicPlayer.loadAndPlay(currentSong.getFilename());
            isCurrentSongPlaying = true;

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

            // Update the UI
            updateSongActionsPanel();
        }
    }

    public void resumeCurrentSong() {
        if (!isCurrentSongPlaying) {
            musicPlayer.resume();
            isCurrentSongPlaying = true;

            // Update the UI
            updateSongActionsPanel();
        }
    }

    public void releaseCurrentSong() {
        musicPlayer.stop();

        if (isCurrentSongPlaying) {
            isCurrentSongPlaying = false;
        }

        currentSong = null;

        // Update the UI
        updateSongActionsPanel();
    }

    public void toggleRandomSongChooser() {
        isCurrentSongChooserRandom = !isCurrentSongChooserRandom;

        // Update the UI
        updateSongActionsPanel();
    }

    public void toggleSongLooping() {
        isCurrentSongLooping = !isCurrentSongLooping;

        // Update the UI
        updateSongActionsPanel();
    }

    // Note : Add a method to automatically choose the next song after the current song has finished playing

    public void selectPreviousSong() {
        // If the current mode is random
        if (isCurrentSongChooserRandom) {
            return;
        }

        // Select the previous song
        currentSong = daoPlaylist.getPreviousSong(currentSong, currentPlaylist);

        // Update the UI
        updateSongPanel();
    }

    public void selectNextSong() {
        // Choose a new song
        Playlist currentPlaylist = daoPlaylist.getSongPlaylist(currentSong);

        if (isCurrentSongChooserRandom) {
            currentSong = daoPlaylist.getNextSong(currentSong, currentPlaylist);
        } else {
            currentSong = daoPlaylist.getRandomSong(currentPlaylist);
        }

        // Update the UI
        updateSongPanel();
    }

    public void selectNewSong() {
        // If the current song is looping
        if (!isCurrentSongLooping) {
            selectNextSong();
        }
    }

    public void addCurrentSongToFavorites() {
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

    public void removeCurrentSongFromFavorites() {
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

    public void addCurrentSongToPlaylist() {
        try {
            Playlist oldPlaylist = daoPlaylist.getSongPlaylist(currentSong);
            String selectedPlaylist = view.promptChooseAddToPlaylist(daoPlaylist.getBasePlaylistsList());

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

    public void removeCurrentSongFromPlaylist() {
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

    public void updatePlaylistJacket(Playlist playlist) {
        byte[] playlistJacket = null;

        if (!playlist.getSongList().isEmpty()) {
            playlistJacket = musicPlayer.getSongIcon(playlist.getSongList().get(0).getFilename());
        } else {
            playlistJacket = daoPlaylist.loadImageAsBytes(Constants.DEFAULT_PLAYLIST_ICON);
        }

        playlist.setIcon(playlistJacket);
    }

    public void createPlaylist() {
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

    public void deletePlaylist() {
        try {
            String playlistToDelete = view.promptChoosePlaylistToDelete(daoPlaylist.getBasePlaylistsList());

            if (playlistToDelete != null) {
                if (currentSong != null) {
                    if (daoPlaylist.getSongPlaylist(currentSong).getTitle().equals(playlistToDelete)) {
                        releaseCurrentSong();
                    }
                }

                // Update currentPlaylist if necessary
                if (currentPlaylist != null && currentPlaylist.getTitle().equals(playlistToDelete)) {
                    currentPlaylist = null;
                }

                // Delete playlist
                daoPlaylist.removePlaylist(daoPlaylist.getPlaylistByName(playlistToDelete));
                daoPlaylist.savePlaylistsToFile();

                // Update the UI
                if (currentView.equals(Constants.PLAYLIST) || currentView.equals(Constants.HOME)) {
                    updateToHome();
                }
            }
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    public void editPlaylist() {
        try {
            String playlistName = view.promptChoosePlaylistToEdit(daoPlaylist.getBasePlaylistsList());

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

    public void openSong() {
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
}
