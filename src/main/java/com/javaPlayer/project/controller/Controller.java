package com.javaPlayer.project.controller;

import com.javaPlayer.project.model.authentication.Authenticator;
import com.javaPlayer.project.model.dao.DAOPlaylist;
import com.javaPlayer.project.model.dao.DAOUser;
import com.javaPlayer.project.model.entity.*;
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
    private DAOPlaylist daoPlayList;
    private DAOUser daoUser;

    // Current displayed data
    private String currentView;
    private String currentPlaylistTitle = null;
    private Playlist currentPlaylist = null; // Note : To continue

    // Current song data
    private Song currentSong = null;
    // Stored temporarily
    private Duration remainingSongDuration;
    private Duration elapsedSongDuration;
    private boolean isCurrentSongFavorite;

    public Controller(JFrameMainWindow view, Authenticator authenticator, DAOUser daoUser, DAOPlaylist daoPlayList) {
        this.view = view;
        this.authenticator = authenticator;
        this.daoUser = daoUser;
        this.daoPlayList = daoPlayList;

        this.view.setController(this);
        this.daoUser.loadUsersFromFile();

        // Authenticate user
        switchUser(authenticate());
        view.setVisible(true);

        // Note : Temporary
//        daoPlayList.addPlaylist(new Playlist("Playlist 1", new ArrayList<>()));
//        daoPlayList.addPlaylist(new Playlist("Playlist 2", new ArrayList<>()));
//        daoPlayList.addPlaylist(new Playlist("Playlist 3", new ArrayList<>()));
//        daoPlayList.getPlaylist("Playlist 1").addSong(new Song("Song 1", new Artist(0, "Artist 1"), "Pop", Duration.ofMinutes(1), LocalDateTime.now(), null));
//        daoPlayList.getPlaylist("Playlist 2").addSong(new Song("Song 2", new Artist(0, "Artist 2"), "Pop", Duration.ofMinutes(2), LocalDateTime.now(), null));
//        daoPlayList.getPlaylist("Playlist 3").addSong(new Song("Song 3", new Artist(0, "Artist 3"), "Pop", Duration.ofMinutes(3), LocalDateTime.now(), null));
//        daoPlayList.getPlaylist("Favorites").addSong(new Song("Song 4", new Artist(0, "Artist 4"), "Pop", Duration.ofMinutes(3), LocalDateTime.now(), null));
//        daoPlayList.getPlaylist("Unclassed songs").addSong(new Song("Song 5", new Artist(0, "Artist 5"), "Pop", Duration.ofMinutes(3), LocalDateTime.now(), null));
//        currentSong = daoPlayList.getPlaylist("Favorites").getSongList().getFirst();
//        daoPlayList.savePlaylistsToFile();

        // Show home menu
        view.updateHomePanel(daoPlayList.getRecentPlaylistsList(Constants.RECENT_PLAYLIST_MINUTES), daoPlayList.getPlaylistsList());
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
            currentPlaylistTitle = "Favorites";
            updateToPlaylist();
        } else if (e.getActionCommand().equals(ControllerActions.PLAYLIST_VIEW)) {
            currentPlaylistTitle = view.getSelectedPlaylistTitle();
            updateToPlaylist();
        } else if (e.getActionCommand().equals(ControllerActions.SWITCH_ACCOUNT)) {
            view.stop();
            daoUser.setCurrentUser(authenticate());
            switchUser(daoUser.getCurrentUser());
            view.run();

            updateToHome();
        } else if (e.getActionCommand().equals(ControllerActions.SETTINGS)) {
            openSettings();
        } else if (e.getActionCommand().equals(ControllerActions.SONG_DETAILS)) {
            showSongDetails();
        } else if (e.getActionCommand().equals(ControllerActions.PLAYLIST_SETTINGS)) {
            showAndUpdatePlaylistSettings();
        } else if (e.getActionCommand().equals(ControllerActions.PAUSE_PLAY)) {
            // Note : To do

            // Pause or play song
            System.out.println("Pause/play triggered");
        } else if (e.getActionCommand().equals(ControllerActions.PREVIOUS)) {
            // Note : To do

            // Play previous song

            // Note : Temporary
            System.out.println("Previous song triggered");
        } else if (e.getActionCommand().equals(ControllerActions.NEXT)) {
            // Note : To do

            // Play next song

            // Note : Temporary
            System.out.println("Next song triggered");
        } else if (e.getActionCommand().equals(ControllerActions.RANDOM)) {
            // Note : To do

            // Count the number of songs in the actual playlist
            // Choose a random number between 1 and the number of songs
            // Play the song at this position in the playlist

            // Note : Temporary
            System.out.println("Random triggered");
        } else if (e.getActionCommand().equals(ControllerActions.LOOP)) {
            // Note : To do

            // Change the loop flag to true

            // Note : Temporary
            System.out.println("Loop triggered");
        } else if (e.getActionCommand().equals(ControllerActions.TOGGLE_FAVORITE)) {
            if (isCurrentSongFavorite) {
                removeCurrentSongFromFavorites();
            } else {
                addCurrentSongToFavorites();
            }
        } else if (e.getActionCommand().equals(ControllerActions.ADD_TO_FAVORITES)) {
            if (!isCurrentSongFavorite) {
                addCurrentSongToFavorites();
            }
        } else if (e.getActionCommand().equals(ControllerActions.ADD_TO_PLAYLIST)) {
            addCurrentSongToPlaylist();
        } else if (e.getActionCommand().equals(ControllerActions.OPEN_SONG)) {
            // Note : To do
            File songFile = view.openFile("Audio file (*.mp3)", "mp3");

            if (songFile != null) {
                // Add song to the All song playlist

                // Note : Temporary
                System.out.println("Song opened : " + songFile.getAbsolutePath());
            }
        } else if (e.getActionCommand().equals(ControllerActions.CREATE_BACKUP)) {
            // Note : To do

            // Export all app data
        } else if (e.getActionCommand().equals(ControllerActions.REMOVE_SONG_FROM_FAVORITES)) {
            if (isCurrentSongFavorite) {
                removeCurrentSongFromFavorites();
                updateSongPanel();
            }
        } else if (e.getActionCommand().equals(ControllerActions.REMOVE_SONG_FROM_PLAYLIST)) {
            removeCurrentSongFromPlaylist();
        } else if (e.getActionCommand().equals(ControllerActions.CREATE_PLAYLIST)) {
            createPlaylist();
        } else if (e.getActionCommand().equals(ControllerActions.DELETE_PLAYLIST)) {
            deletePlaylist();
        } else if (e.getActionCommand().equals(ControllerActions.EXPORT_PLAYLIST)) {
            // Note : to do

            // Export the current playlist

            // Note : Temporary
            System.out.println("Export playlist triggered");
        } else if (e.getActionCommand().equals(ControllerActions.IMPORT_PLAYLIST)) {
            // Note : to do

            // Import a playlist

            // Note : Temporary
            System.out.println("Import playlist triggered");
        } else if (e.getActionCommand().equals(ControllerActions.SEARCH_SONG)) {
            // Note : To do

            // Retrieve the word to search for
            // Search for all songs containing the given word
            // Update the UI

            // Note : Temporary
            System.out.println("Search triggered");
        } else if (e.getActionCommand().equals(ControllerActions.PLAY_SELECTED_SONG)) {
            int songId = (int) e.getSource();

            // Retrieve selected song
            //
            // Play selected song

            System.out.println("Song with id " + songId + " selected");
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
                isAuthenticated = authenticator.authenticate(credentials.getUsername(), credentials.getPassword());

                if (!isAuthenticated) {
                    view.showMessage("Password incorrect");
                }
            }
        } while (!isAuthenticated);

        return daoUser.getUserByPseudo(credentials.getUsername());
    }

    public void switchUser(User user) {
        daoUser.setCurrentUser(user);
        daoPlayList.loadPlaylistsConfigFile(user.getId());
        daoPlayList.loadPlaylistsFromFile();
    }

    public void run() {
        view.run();
    }

    public void stop() {
        view.stop();
    }

    public void updateToHome() {
        view.updateHomePanel(daoPlayList.getRecentPlaylistsList(Constants.RECENT_PLAYLIST_MINUTES), daoPlayList.getPlaylistsList());
        view.showHome();
        currentView = Constants.HOME;
    }

    public void updateToSearch() {
        view.updateSearchPanel(new ArrayList<Song>());
        view.showSearch();
        currentView = Constants.SEARCH;
    }

    public void updateToPlaylist() {
        Playlist currentPlaylist = daoPlayList.getPlaylistByName(currentPlaylistTitle);

        view.updatePlaylistPanel(currentPlaylist);
        view.showPlaylist();
        currentView = Constants.PLAYLIST;
    }

    public void openSettings() {
        // Retrieve user pseudo and password
        Settings settings = view.showAndGetSettings(daoUser.getCurrentUser().getPseudo(), daoUser.getCurrentUser().getPassword());

        if (settings != null) {
            // Update user settings
            // Note : See how to do
//                authenticator.changePseudo(settings.getUserPseudo());
            if (settings.isDeletingAllData()) {
                // Delete playlist file from user
                // Delete user from config file
            }

            // Note : To continue
            // Update user pseudo and user password

            // Note : Temporary
            System.out.println("Settings updated : {" + settings.getUserPseudo() + ", " + settings.getUserPassword() + ", " + settings.isDeletingAllData() + "}");
        }
    }

    public void showSongDetails() {
        if (currentSong != null) {
            // Format the added date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = currentSong.getAddedDate().format(formatter);

            // Ask to the view to display song details
            view.showSongDetails(currentSong.getTitle(),
                    currentSong.getArtist().getPseudo(),
                    daoPlayList.getSongPlaylist(currentSong).getTitle(),
                    formattedDateTime,
                    currentSong.getDurationToString());
        } else {
            view.showMessage("No song playing");
        }
    }

    public void showAndUpdatePlaylistSettings() {
        // Retrieve playlist data
        Playlist playlist = daoPlayList.getPlaylistByName(currentPlaylistTitle);

        if (playlist == null) {
            view.showMessage("No playlist selected");
            return;
        }

        PlaylistSettings playlistSettings = view.showAndGetPlaylistSettings(playlist.getTitle(), daoUser.getCurrentUser().getPseudo());

        if (playlistSettings != null) {
            // If the user wants to delete the playlist
            if (playlistSettings.isDeletingPlaylist()) {
                daoPlayList.removePlaylist(playlist.getTitle());

                // Update the UI
                if ((currentView.equals(Constants.PLAYLIST)) || currentView.equals(Constants.HOME)) {
                    updateToHome();
                }

                currentPlaylistTitle = null;
            } else { // If the user wants to update the playlist
                playlist.setTitle(playlistSettings.getPlaylistName());

                currentPlaylistTitle = playlist.getTitle();

                // Update the UI
                if (currentView.equals(Constants.PLAYLIST)) {
                    updateToPlaylist();
                } else if (currentView.equals(Constants.HOME)) { // If the view is displaying the home page
                    updateToHome();
                }
            }

            daoPlayList.savePlaylistsToFile();

            // Note : Temporary
            System.out.println("Playlist updated : {" + playlistSettings.getPlaylistName() + ", " + playlistSettings.isDeletingPlaylist() + "}");
        }
    }

    public void updateSongPanel() {
        view.updateSongPanel(currentSong.getTitle(),
                currentSong.getArtist().getPseudo(),
                null, // Note : Temporary
                currentSong.getDurationToString(),
                formatDuration(remainingSongDuration),
                formatDuration(elapsedSongDuration),
                isCurrentSongFavorite);
    }

    public void addCurrentSongToFavorites() {
        // Remove current song from the current playlist
        Playlist currentSongPlaylist = daoPlayList.getSongPlaylist(currentSong);
        currentSongPlaylist.removeSong(currentSong);

        // Add current song to the Favorites playlist
        daoPlayList.getPlaylistByName("Favorites").addSong(currentSong);
        daoPlayList.savePlaylistsToFile();

        // Update current song flag
        isCurrentSongFavorite = true;

        // Update the UI
        if (currentView.equals(Constants.PLAYLIST) && currentPlaylistTitle.equals("Favorites")) {
            updateToPlaylist();
        }

        updateSongPanel();

        // Note : Temporary
        System.out.println("Song " + currentSong.getTitle() + " added to favorites");
    }

    public void removeCurrentSongFromFavorites() {
        // Remove current song from the Favorites playlist
        Playlist FavoritesPlaylist = daoPlayList.getPlaylistByName("Favorites");
        FavoritesPlaylist.removeSong(currentSong);

        // Add current song to the Unclassed songs playlist
        daoPlayList.getPlaylistByName("Unclassed songs").addSong(currentSong);
        daoPlayList.savePlaylistsToFile();

        // Update current song flag
        isCurrentSongFavorite = false;

        // Update the UI
        if (currentView.equals(Constants.PLAYLIST) && currentPlaylistTitle.equals("Favorites")) {
            updateToPlaylist();
        }

        updateSongPanel();

        // Note : Temporary
        System.out.println("Song " + currentSong.getTitle() + " removed from favorites");
    }

    public void addCurrentSongToPlaylist() {
        // Note : Temporary
        ArrayList<String> playlistTitleList = daoPlayList.getPlaylistsTitleList();

        String selectedPlaylist = view.promptChooseAddToPlaylist(playlistTitleList);

        if (selectedPlaylist != null) {
            // Remove current song from the current playlist
            daoPlayList.getSongPlaylist(currentSong).removeSong(currentSong);
            // Add current song to selected playlist
            daoPlayList.getPlaylistByName(selectedPlaylist).addSong(currentSong);
            daoPlayList.savePlaylistsToFile();

            // Update the UI
            if (currentView.equals(Constants.HOME)) {
                updateToHome();
            } else if (currentView.equals(Constants.PLAYLIST)) {
                updateToPlaylist();
            }

            updateSongPanel();

            // Note : Temporary
            System.out.println("Song " + currentSong.getTitle() + " added to playlist : " + selectedPlaylist);
        }
    }

    public void removeCurrentSongFromPlaylist() {
        // Remove song from the current playlist
        daoPlayList.getSongPlaylist(currentSong).removeSong(currentSong);
        // Add song to the Unclassed songs playlist
        daoPlayList.getPlaylistByName("Unclassed songs").addSong(currentSong);
        daoPlayList.savePlaylistsToFile();

        view.showMessage("Song removed from playlist and placed in Unclassed songs playlist");
        System.out.println("Song " + currentSong.getTitle() + " removed from playlist");

        // Update the UI
        if (currentView.equals(Constants.PLAYLIST)) {
            updateToPlaylist();
        }

        updateSongPanel();
    }

    public void createPlaylist() {
        // Ask for the new playlist name
        String playlistName = view.promptToCreatePlaylist();

        if (playlistName != null) {
            // Create a new playlist
            daoPlayList.createPlaylist(playlistName);
            daoPlayList.savePlaylistsToFile();
            // Update the UI
            currentPlaylistTitle = playlistName;
            updateToPlaylist();

            // Note : Temporary
            System.out.println("Playlist created : " + playlistName);
        }
    }

    public void deletePlaylist() {
        ArrayList<String> playlistTitleList = daoPlayList.getPlaylistsTitleList();

        String playlistToDelete = view.promptChoosePlaylistToDelete(playlistTitleList);

        if (playlistToDelete != null) {
            // Delete playlist
            daoPlayList.removePlaylist(playlistToDelete);
            daoPlayList.savePlaylistsToFile();

            // If the view is displaying the playlist to be deleted
            if ((currentView.equals(Constants.PLAYLIST) && currentPlaylistTitle.equals(playlistToDelete)) || currentView.equals(Constants.HOME)) {
                updateToHome();
            }

            // Note : Temporary
            System.out.println("Playlist deleted : " + playlistToDelete);
        }
    }
}
