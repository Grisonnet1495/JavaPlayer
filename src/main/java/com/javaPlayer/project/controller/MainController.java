package com.javaPlayer.project.controller;

import com.javaPlayer.project.model.authentication.Authenticator;
import com.javaPlayer.project.model.dao.DAOPlaylist;
import com.javaPlayer.project.model.entity.*;
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

public final class MainController implements ActionListener {
    private DAOPlaylist daoPlayList;
    private JFrameMainWindow view;
    private Authenticator authenticator;

    // Current displayed data
    private User currentUser;
    private String currentView;
    private String currentPlaylistTitle = null;

    // Current song data
    private Song currentSong = null;
    // Stored temporarily
    private Duration remainingSongDuration;
    private Duration elapsedSongDuration;
    private boolean isCurrentSongFavorite;

    public MainController(JFrameMainWindow view, Authenticator authenticator) {
        this.view = view;
        this.view.setController(this);
        this.authenticator = authenticator;

        // Authenticate user
        currentUser = authenticate();
        switchUser(currentUser);
        view.setVisible(true);

        // Note : Temporary
        daoPlayList.addPlaylist(new Playlist("Playlist 1", new ArrayList<>()));
        daoPlayList.addPlaylist(new Playlist("Playlist 2", new ArrayList<>()));
        daoPlayList.addPlaylist(new Playlist("Playlist 3", new ArrayList<>()));
        daoPlayList.getPlaylist("Playlist 1").addSong(new Song(0, "Song 1", new Artist(0, "Artist 1"), "Pop", Duration.ofMinutes(1), LocalDateTime.now()));
        daoPlayList.getPlaylist("Playlist 2").addSong(new Song(0, "Song 2", new Artist(0, "Artist 2"), "Pop", Duration.ofMinutes(2), LocalDateTime.now()));
        daoPlayList.getPlaylist("Playlist 3").addSong(new Song(0, "Song 3", new Artist(0, "Artist 3"), "Pop", Duration.ofMinutes(3), LocalDateTime.now()));
        daoPlayList.getPlaylist("Favorites").addSong(new Song(0, "Song 4", new Artist(0, "Artist 4"), "Pop", Duration.ofMinutes(3), LocalDateTime.now()));
        daoPlayList.getPlaylist("Unclassed songs").addSong(new Song(0, "Song 5", new Artist(0, "Artist 5"), "Pop", Duration.ofMinutes(3), LocalDateTime.now()));
        currentSong = daoPlayList.getPlaylist("Favorites").getSongList().getFirst();

        // Show home menu
        view.updateHomePanel(daoPlayList.getRecentPlaylistsTitleList(Constants.RECENT_PLAYLIST_MINUTES), daoPlayList.getPlaylistsTitleList());
        view.showHome();
        currentView = Constants.HOME;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(MainControllerActions.HOME_VIEW)) {
            updateToHome();
        } else if (e.getActionCommand().equals(MainControllerActions.SEARCH_VIEW)) {
            updateToSearch();
        } else if (e.getActionCommand().equals(MainControllerActions.FAVORITES_VIEW)) {
            currentPlaylistTitle = "Favorites";
            updateToPlaylist();
        } else if (e.getActionCommand().equals(MainControllerActions.PLAYLIST_VIEW)) {
            currentPlaylistTitle = view.getSelectedPlaylistTitle();
            updateToPlaylist();
        } else if (e.getActionCommand().equals(MainControllerActions.SWITCH_ACCOUNT)) {
            view.stop();
            currentUser = authenticate();
            switchUser(currentUser);
            view.run();

            updateToHome();
        } else if (e.getActionCommand().equals(MainControllerActions.SETTINGS)) {
            openSettings();
        } else if (e.getActionCommand().equals(MainControllerActions.SONG_DETAILS)) {
            showSongDetails();
        } else if (e.getActionCommand().equals(MainControllerActions.PLAYLIST_SETTINGS)) {
            showAndUpdatePlaylistSettings();
        } else if (e.getActionCommand().equals(MainControllerActions.PAUSE_PLAY)) {
            // Note : To do

            // Pause or play song
            System.out.println("Pause/play triggered");
        } else if (e.getActionCommand().equals(MainControllerActions.PREVIOUS)) {
            // Note : To do

            // Play previous song

            // Note : Temporary
            System.out.println("Previous song triggered");
        } else if (e.getActionCommand().equals(MainControllerActions.NEXT)) {
            // Note : To do

            // Play next song

            // Note : Temporary
            System.out.println("Next song triggered");
        } else if (e.getActionCommand().equals(MainControllerActions.RANDOM)) {
            // Note : To do

            // Count the number of songs in the actual playlist
            // Choose a random number between 1 and the number of songs
            // Play the song at this position in the playlist

            // Note : Temporary
            System.out.println("Random triggered");
        } else if (e.getActionCommand().equals(MainControllerActions.LOOP)) {
            // Note : To do

            // Change the loop flag to true

            // Note : Temporary
            System.out.println("Loop triggered");
        } else if (e.getActionCommand().equals(MainControllerActions.TOGGLE_FAVORITE)) {
            if (isCurrentSongFavorite) {
                removeCurrentSongFromFavorites();
            } else {
                addCurrentSongToFavorites();
            }
        } else if (e.getActionCommand().equals(MainControllerActions.ADD_TO_FAVORITES)) {
            if (!isCurrentSongFavorite) {
                addCurrentSongToFavorites();
            }
        } else if (e.getActionCommand().equals(MainControllerActions.ADD_TO_PLAYLIST)) {
            addCurrentSongToPlaylist();
        } else if (e.getActionCommand().equals(MainControllerActions.OPEN_SONG)) {
            // Note : To do
            File songFile = view.openFile("Audio file (*.mp3)", "mp3");

            if (songFile != null) {
                // Add song to the All song playlist

                // Note : Temporary
                System.out.println("Song opened : " + songFile.getAbsolutePath());
            }
        } else if (e.getActionCommand().equals(MainControllerActions.CREATE_BACKUP)) {
            // Note : To do

            // Export all app data
        } else if (e.getActionCommand().equals(MainControllerActions.REMOVE_SONG_FROM_FAVORITES)) {
            if (isCurrentSongFavorite) {
                removeCurrentSongFromFavorites();
                updateSongPanel();
            }
        } else if (e.getActionCommand().equals(MainControllerActions.REMOVE_SONG_FROM_PLAYLIST)) {
            removeCurrentSongFromPlaylist();
        } else if (e.getActionCommand().equals(MainControllerActions.CREATE_PLAYLIST)) {
            createPlaylist();
        } else if (e.getActionCommand().equals(MainControllerActions.DELETE_PLAYLIST)) {
            deletePlaylist();
        } else if (e.getActionCommand().equals(MainControllerActions.EXPORT_PLAYLIST)) {
            // Note : to do

            // Export the current playlist

            // Note : Temporary
            System.out.println("Export playlist triggered");
        } else if (e.getActionCommand().equals(MainControllerActions.IMPORT_PLAYLIST)) {
            // Note : to do

            // Import a playlist

            // Note : Temporary
            System.out.println("Import playlist triggered");
        } else if (e.getActionCommand().equals(MainControllerActions.SEARCH_SONG)) {
            // Note : To do

            // Retrieve the word to search for
            // Search for all songs containing the given word
            // Update the UI

            // Note : Temporary
            System.out.println("Search triggered");
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
                    isAuthenticated = true;
                }
            } else {
                isAuthenticated = authenticator.authenticate(credentials.getUsername(), credentials.getPassword());

                if (!isAuthenticated) {
                    view.showMessage("Password incorrect");
                }
            }
        } while (!isAuthenticated);

        return new User(credentials.getUsername(), credentials.getPassword());
    }

    public void switchUser(User user) {
        daoPlayList = new DAOPlaylist(user.getPseudo());
    }

    public void run() {
        view.run();
    }

    public void stop() {
        view.stop();
    }

    public void updateToHome() {
        view.updateHomePanel(daoPlayList.getRecentPlaylistsTitleList(Constants.RECENT_PLAYLIST_MINUTES), daoPlayList.getPlaylistsTitleList());
        view.showHome();
        currentView = Constants.HOME;
    }

    public void updateToSearch() {
        view.updateSearchPanel(new ArrayList<Song>());
        view.showSearch();
        currentView = Constants.SEARCH;
    }

    public void updateToPlaylist() {
        Playlist currentPlaylist = daoPlayList.getPlaylist(currentPlaylistTitle);

        view.updatePlaylistPanel(currentPlaylist);
        view.showPlaylist();
        currentView = Constants.PLAYLIST;
    }

    public void openSettings() {
        // Retrieve user pseudo and password
        Settings settings = view.showAndGetSettings(currentUser.getPseudo(), currentUser.getPassword());

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
                    formatDuration(currentSong.getDuration()));
        } else {
            view.showMessage("No song playing");
        }
    }

    public void showAndUpdatePlaylistSettings() {
        // Retrieve playlist data
        Playlist playlist = daoPlayList.getPlaylist(currentPlaylistTitle);

        if (playlist == null) {
            view.showMessage("No playlist selected");
            return;
        }

        PlaylistSettings playlistSettings = view.showAndGetPlaylistSettings(playlist.getTitle(), currentUser.getPseudo());

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

            // Note : Temporary
            System.out.println("Playlist updated : {" + playlistSettings.getPlaylistName() + ", " + playlistSettings.isDeletingPlaylist() + "}");
        }
    }

    public void updateSongPanel() {
        view.updateSongPanel(currentSong.getTitle(),
                currentSong.getArtist().getPseudo(),
                null, // Note : Temporary
                formatDuration(currentSong.getDuration()),
                formatDuration(remainingSongDuration),
                formatDuration(elapsedSongDuration),
                isCurrentSongFavorite);
    }

    public void addCurrentSongToFavorites() {
        // Remove current song from the current playlist
        Playlist currentSongPlaylist = daoPlayList.getSongPlaylist(currentSong);
        currentSongPlaylist.removeSong(currentSong);

        // Add current song to the Favorites playlist
        daoPlayList.getPlaylist("Favorites").addSong(currentSong);

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
        Playlist FavoritesPlaylist = daoPlayList.getPlaylist("Favorites");
        FavoritesPlaylist.removeSong(currentSong);

        // Add current song to the Unclassed songs playlist
        daoPlayList.getPlaylist("Unclassed songs").addSong(currentSong);

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
            daoPlayList.getPlaylist(selectedPlaylist).addSong(currentSong);

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
        daoPlayList.getPlaylist("Unclassed songs").addSong(currentSong);

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
            daoPlayList.addPlaylist(new Playlist(playlistName, new ArrayList<>()));
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

            // If the view is displaying the playlist to be deleted
            if ((currentView.equals(Constants.PLAYLIST) && currentPlaylistTitle.equals(playlistToDelete)) || currentView.equals(Constants.HOME)) {
                updateToHome();
            }

            // Note : Temporary
            System.out.println("Playlist deleted : " + playlistToDelete);
        }
    }
}
