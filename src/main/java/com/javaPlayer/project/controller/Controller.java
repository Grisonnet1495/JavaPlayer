package com.javaPlayer.project.controller;

import com.javaPlayer.project.model.authentication.Authenticator;
import com.javaPlayer.project.model.dao.DAOPlaylist;
import com.javaPlayer.project.model.dao.DAOUser;
import com.javaPlayer.project.model.entity.*;
import com.javaPlayer.project.model.player.IMusicPlayer;
import com.javaPlayer.project.utils.Constants;
import com.javaPlayer.project.view.GUI.JFrameMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.javaPlayer.project.utils.DurationFormatter.formatDuration;

public final class Controller implements ActionListener {
    private JFrameMainWindow view;
    private Authenticator authenticator;
    private DAOPlaylist daoPlaylist;
    private DAOUser daoUser;
    private IMusicPlayer musicPlayer;

    // Current displayed data
    private String currentView;
    private Playlist currentPlaylist = null; // Note : To continue
    private Song currentSong = null;
    private boolean isCurrentSongFavorite;
    private boolean isCurrentSongChooserRandom = false;
    private boolean isCurrentSongLooping = false;
    private boolean isCurrentSongPlaying = false;

    // Note : Not used yet
    private Duration remainingSongDuration;
    private Duration elapsedSongDuration;

    public Controller(JFrameMainWindow view, Authenticator authenticator, DAOUser daoUser, DAOPlaylist daoPlaylist, IMusicPlayer musicPlayer) {
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
        currentSong = daoPlaylist.getLastPlayedSong();

        if (currentSong == null) {
            currentSong = daoPlaylist.getFirstSong();
        }

        if (currentSong != null) {
            currentPlaylist = daoPlaylist.getSongPlaylist(currentSong);
        }

        updateSongPanel();
        updateSongActionsPanel();

        // Show home menu
        view.updateHomePanel(daoPlaylist.getRecentPlaylistsList(Constants.RECENT_PLAYLIST_MINUTES), daoPlaylist.getPlaylistsList());
        view.showHome();
        currentView = Constants.HOME;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ControllerActions.HOME_VIEW)) {
            updateToHome();
        } else if (e.getActionCommand().equals(ControllerActions.SEARCH_VIEW)) {
            updateToSearch();
        } else if (e.getActionCommand().equals(ControllerActions.FAVORITES_VIEW)) {
            currentPlaylist = daoPlaylist.getPlaylistByName("Favorites");
            updateToPlaylist();
        } else if (e.getActionCommand().equals(ControllerActions.PLAYLIST_VIEW)) {
            currentPlaylist = daoPlaylist.getPlaylistByName(view.getSelectedPlaylistTitle());
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
        } else if (e.getActionCommand().equals(ControllerActions.SWITCH_ACCOUNT)) {
            view.stop();
            switchUser(authenticate());
            view.run();

            updateToHome();
        } else if (e.getActionCommand().equals(ControllerActions.SETTINGS)) {
            openAndUpdateSettings();
        } else if (e.getActionCommand().equals(ControllerActions.SONG_DETAILS)) {
            if (currentSong == null) {
                view.showMessage("No song selected");
                return;
            }

            showSongDetails();
        } else if (e.getActionCommand().equals(ControllerActions.PLAYLIST_SETTINGS)) {
            if (currentPlaylist == null) {
                view.showMessage("No playlist selected");
                return;
            }

            showAndUpdatePlaylistSettings();
        } else if (e.getActionCommand().equals(ControllerActions.PAUSE_PLAY)) {
            if (currentSong == null) {
                view.showMessage("No song selected");
                return;
            }

            if (isCurrentSongPlaying) {
                pauseCurrentSong();
            } else {
                resumeCurrentSong();
            }
        } else if (e.getActionCommand().equals(ControllerActions.PREVIOUS)) {
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

            pauseCurrentSong();
            selectPreviousSong();
            playCurrentSong();
        } else if (e.getActionCommand().equals(ControllerActions.NEXT)) {
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

            pauseCurrentSong();
            selectNextSong();
            playCurrentSong();
        } else if (e.getActionCommand().equals(ControllerActions.RANDOM)) {
            toggleRandomSongChooser();
        } else if (e.getActionCommand().equals(ControllerActions.LOOP)) {
            toggleSongLooping();
        } else if (e.getActionCommand().equals(ControllerActions.TOGGLE_FAVORITE)) {
            if (currentSong == null) {
                view.showMessage("No song selected");
                return;
            }

            if (isCurrentSongFavorite) {
                removeCurrentSongFromFavorites();
            } else {
                addCurrentSongToFavorites();
            }
        } else if (e.getActionCommand().equals(ControllerActions.ADD_TO_FAVORITES)) {
            if (currentSong == null) {
                view.showMessage("No song selected");
                return;
            }

            if (!isCurrentSongFavorite) {
                addCurrentSongToFavorites();
            }
        } else if (e.getActionCommand().equals(ControllerActions.ADD_TO_PLAYLIST)) {
            if (currentSong == null) {
                view.showMessage("No song selected");
                return;
            }

            if (currentSong == null) {
                view.showMessage("No song selected");
                return;
            }

            addCurrentSongToPlaylist();
        } else if (e.getActionCommand().equals(ControllerActions.OPEN_SONG)) {
            // Note : To do (Sacha)
            File songFile = view.openFile("Audio file (*.mp3)", "mp3");

            if (songFile != null) {
                // Add song to the All song playlist

                // Note : Temporary
                System.out.println("Song opened : " + songFile.getAbsolutePath());
            }
        } else if (e.getActionCommand().equals(ControllerActions.EXPORT_SONG)) {
            String newFilename = view.saveFile();

            if (newFilename != null) {
                daoPlaylist.exportSong(currentSong, newFilename);
            }
        } else if (e.getActionCommand().equals(ControllerActions.REMOVE_SONG_FROM_FAVORITES)) {
            if (currentSong == null) {
                view.showMessage("No song selected");
                return;
            }

            if (isCurrentSongFavorite) {
                removeCurrentSongFromFavorites();
                updateSongPanel();
            }
        } else if (e.getActionCommand().equals(ControllerActions.REMOVE_SONG_FROM_PLAYLIST)) {
            if (currentSong == null) {
                view.showMessage("No song selected");
                return;
            }

            removeCurrentSongFromPlaylist();
        } else if (e.getActionCommand().equals(ControllerActions.CREATE_PLAYLIST)) {
            createPlaylist();
        } else if (e.getActionCommand().equals(ControllerActions.DELETE_PLAYLIST)) {
            deletePlaylist();
        } else if (e.getActionCommand().equals(ControllerActions.SEARCH_SONG)) {
            // Note : To do (Sacha)

            // Retrieve the word to search for
            // Search for all songs containing the given word
            // Update the UI

            // Note : Temporary
            System.out.println("Search triggered");
        } else if (e.getActionCommand().equals(ControllerActions.PLAY_SELECTED_SONG)) {
            // Retrieve the song selected by the user
            int songId = (int) e.getSource();
            Song selectedSong = daoPlaylist.getSongById(songId);

            if (selectedSong != null) {
                // Set the current song and play it
                currentSong = selectedSong;
                isCurrentSongFavorite = daoPlaylist.isSongInFavoritesPlaylist(currentSong);
                daoPlaylist.setLastPlayedSong(selectedSong);
                playCurrentSong();
            } else {
                view.showMessage("Song not found");
            }
        } else if (e.getActionCommand().equals(ControllerActions.EXIT_APP)) {
            stop();
            clearResourcesAndExit();
        } else {
            view.showMessage("Button not implemented !");
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
                clearResourcesAndExit();
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

    public void clearResourcesAndExit() {
        musicPlayer.release();
        view.dispose();
        System.exit(0);
    }

    public void updateToHome() {
        view.updateHomePanel(daoPlaylist.getRecentPlaylistsList(Constants.RECENT_PLAYLIST_MINUTES), daoPlaylist.getPlaylistsList());
        view.showHome();
        currentView = Constants.HOME;
    }

    public void updateToSearch() {
        view.updateSearchPanel(new ArrayList<Song>());
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
                    daoPlaylist.removePlaylist(currentPlaylist.getTitle());
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
                    } else if (currentView.equals(Constants.HOME)) { // If the view is displaying the home page
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
                    null, // Note : Temporary
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
        // Note : To continue (Sacha)
        // Play the current song playing with the Music player
        isCurrentSongPlaying = true;

        updateSongActionsPanel();
    }

    public void pauseCurrentSong() {
        // Note : To continue (Sacha)
        // Pause the current song playing with the Music player
        isCurrentSongPlaying = false;

        updateSongActionsPanel();
    }

    public void resumeCurrentSong() {
        // Note : To continue (Sacha)
        // Resume the current song playing with the Music player
        isCurrentSongPlaying = true;

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
            daoPlaylist.getRandomSong(currentPlaylist);
        }

        // Update the UI
        updateSongPanel();
    }

    public void selectNewSong() {
        // If the current song is looping
        if (isCurrentSongLooping) {
            return;
        }

        selectNextSong();
    }

    public void addCurrentSongToFavorites() {
        try {
            // Remove current song from the current playlist
            daoPlaylist.moveSongToPlaylist(currentSong, daoPlaylist.getSongPlaylist(currentSong), daoPlaylist.getPlaylistByName("Favorites"));
            daoPlaylist.savePlaylistsToFile();

            // Update current song flag
            isCurrentSongFavorite = true;

            // Update the UI
            if (currentView.equals(Constants.PLAYLIST)) {
                updateToPlaylist();
            }

            updateSongPanel();
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    public void removeCurrentSongFromFavorites() {
        // Remove current song from the Favorites playlist
        try {
            if (!daoPlaylist.getSongPlaylist(currentSong).getTitle().equals("Favorites")) {
                view.showMessage("Song is not in Favorites playlist");
            }

            if (currentSong != null) {
                daoPlaylist.moveSongToPlaylist(currentSong, daoPlaylist.getSongPlaylist(currentSong), daoPlaylist.getPlaylistByName("Unclassed songs"));
                daoPlaylist.savePlaylistsToFile();

                // Update current song flag
                isCurrentSongFavorite = false;

                // Update the UI
                if (currentView.equals(Constants.PLAYLIST)) {
                    updateToPlaylist();
                }

                updateSongPanel();
            } else {
                view.showMessage("No song selected");
            }
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    public void addCurrentSongToPlaylist() {
        try {
            ArrayList<String> playlistTitleList = daoPlaylist.getPlaylistsTitleList();

            String selectedPlaylist = view.promptChooseAddToPlaylist(playlistTitleList);

            if (selectedPlaylist != null) {
                daoPlaylist.moveSongToPlaylist(currentSong, daoPlaylist.getSongPlaylist(currentSong), daoPlaylist.getPlaylistByName(selectedPlaylist));
                daoPlaylist.savePlaylistsToFile();

                // Update the UI
                if (currentView.equals(Constants.PLAYLIST)) {
                    updateToPlaylist();
                }

                updateSongPanel();
            }
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    public void removeCurrentSongFromPlaylist() {
        try {
            daoPlaylist.moveSongToPlaylist(currentSong, daoPlaylist.getSongPlaylist(currentSong), daoPlaylist.getPlaylistByName("Unclassed songs"));
            daoPlaylist.savePlaylistsToFile();

            view.showMessage("Song removed from playlist and placed in Unclassed songs playlist");

            // Update the UI
            if (currentView.equals(Constants.PLAYLIST)) {
                updateToPlaylist();
            }

            updateSongPanel();
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }

    public void createPlaylist() {
        try {
            // Ask for the new playlist name
            String playlistName = view.promptToCreatePlaylist();

            if (playlistName != null) {
                // Create a new playlist
                daoPlaylist.createPlaylist(playlistName);
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
            ArrayList<String> playlistTitleList = daoPlaylist.getPlaylistsTitleList();

            String playlistToDelete = view.promptChoosePlaylistToDelete(playlistTitleList);

            if (playlistToDelete != null) {
                if (currentSong != null) {
                    if (daoPlaylist.getSongPlaylist(currentSong).getTitle().equals(playlistToDelete)) {
                        // Note : Stop Music player
                        currentSong = null;
                        updateSongPanel();
                    }
                }

                // Update currentSong if necessary
                if (currentSong != null && daoPlaylist.getSongPlaylist(currentSong).getTitle().equals(playlistToDelete)) {
                    currentSong = null;
                }

                // Update currentPlaylist if necessary
                if (currentPlaylist != null && currentPlaylist.getTitle().equals(playlistToDelete)) {
                    currentPlaylist = null;
                }

                // Delete playlist
                daoPlaylist.removePlaylist(playlistToDelete);
                daoPlaylist.savePlaylistsToFile();

                // Update the UI
                if ((currentView.equals(Constants.PLAYLIST) && currentPlaylist.getTitle().equals(playlistToDelete)) || currentView.equals(Constants.HOME)) {
                    updateToHome();
                }
            }
        } catch (Exception e) {
            view.showMessage(e.getMessage());
        }
    }
}
