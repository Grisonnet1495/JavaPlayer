//package com.javaPlayer.project.view.console;
//
//import com.javaPlayer.project.controller.Controller;
//import com.javaPlayer.project.controller.ControllerActions;
//import com.javaPlayer.project.model.entity.*;
//import com.javaPlayer.project.view.ViewMainWindow;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class MainConsoleWindow implements ViewMainWindow {
//    private Scanner scanner;
//    private Controller controller;
//    private ArrayList<Playlist> recentPlaylistsList;
//    private ArrayList<Playlist> allPlaylistsList;
//    private Playlist currentPlaylist;
//    private ArrayList<Song> currentSongList;
//    private String selectedPlaylistTitle;
//
//    public MainConsoleWindow() {
//        scanner = new Scanner(System.in);
//    }
//
//    @Override
//    public Credentials promptForCredentials() {
////        Credentials credentials;
////
////
////
////        return credentials;
//        return null;
//    }
//
//    @Override
//    public void updateHomePanel(ArrayList<Playlist> recentPlaylistsList, ArrayList<Playlist> allPlaylistsList) {
//        this.recentPlaylistsList = recentPlaylistsList;
//        this.allPlaylistsList = allPlaylistsList;
//    }
//
//    @Override
//    public void updatePlaylistPanel(Playlist playlist) {
//        currentPlaylist = playlist;
//    }
//
//    @Override
//    public void updateSearchPanel(ArrayList<Song> songList) {
//        currentSongList = songList;
//    }
//
//    @Override
//    public void updateSongPanel(String songTitle, String artistPseudo, byte[] songIcon, String duration, String elapsedTime, String remainingTime, boolean isSongFavorite) {
//        System.out.println();
//        System.out.println("--------------- Song panel ---------------");
//        System.out.println("Title : " + songTitle);
//        System.out.println("Artist : " + artistPseudo);
//        System.out.println("Duration : " + duration);
//        System.out.println("Elapsed time : " + elapsedTime);
//        System.out.println("Remaining time : " + remainingTime);
//        System.out.println("Favorite : " + isSongFavorite);
//    }
//
//    @Override
//    public void updateSongActionsPanel(boolean isRandom, boolean isPreviousSongPossible, boolean isLooping, boolean isPlaying) {
//        // Note : To do
//    }
//
//    @Override
//    public String getSelectedPlaylistTitle() {
//        return selectedPlaylistTitle;
//    }
//
////    @Override
////    public void clearSelectedPlaylistTitle() {
////        selectedPlaylistTitle = null;
////    }
//
//    @Override
//    public void showHome() {
//        System.out.println();
//        System.out.println("--------------- Home ---------------");
//        System.out.println("Recent playlists :");
//        for (int i = 0; i < recentPlaylistsList.size(); i++) {
//            System.out.println((i + 1) + " : " + recentPlaylistsList.get(i).getTitle());
//        }
//        System.out.println("All playlists :");
//        for (int i = recentPlaylistsList.size(); i < recentPlaylistsList.size() + allPlaylistsList.size(); i++) {
//            System.out.println((i + 1) + " : " + allPlaylistsList.get(i).getTitle());
//        }
//        System.out.println("Choose a playlist to open (or 0 to cancel) :");
//
//        boolean entryCorrect = true;
//
//        do {
//            try {
//                int choice = promptConsoleForInt();
//
//                if (choice > 0 && choice <= recentPlaylistsList.size()) {
//                    controller.actionPerformed(new ActionEvent(this, 0, recentPlaylistsList.get(choice - 1).getTitle()));
//                } else if (choice > recentPlaylistsList.size() && choice <= recentPlaylistsList.size() + allPlaylistsList.size()) {
//                    controller.actionPerformed(new ActionEvent(this, 0, allPlaylistsList.get(choice - recentPlaylistsList.size() - 1).getTitle()));
//                } else {
//                    entryCorrect = false;
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Error : you must enter a number between 1 and " + recentPlaylistsList.size() + allPlaylistsList.size() + " !");
//                entryCorrect = false;
//            }
//        } while (!entryCorrect);
//    }
//
//    @Override
//    public void showSearch() {
//        System.out.println();
//        System.out.println("--------------- Search ---------------");
//        System.out.println("Write the word you want to search : ");
//
//        String searchWord = promptConsoleForString();
//
//        if (!searchWord.isEmpty()) {
//            controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.SEARCH_SONG));
//        }
//    }
//
//    public void updateSearchTable(ArrayList<Song> songList) {
//
//    }
//
//    @Override
//    public void showPlaylist() {
//
//    }
//
//    @Override
//    public Settings showAndGetSettings(String userPseudo, String userPassword) {
//        System.out.println();
//        System.out.println("--------------- Settings ---------------");
//        System.out.println("User pseudo : " + userPseudo);
//        System.out.print("User password : ");
//        for (int i = 0; i < userPassword.length(); i++) {
//            System.out.print("*");
//        }
//        System.out.println();
//        System.out.println("Choose the action to do on the settings (or 0 to cancel) :");
//        System.out.println("1 : Change user pseudo");
//        System.out.println("2 : Change user password");
//        System.out.println("3 : Delete all app data");
//
//        do {
//            try {
//                System.out.println("Enter your choice : ");
//                int choice = promptConsoleForInt();
//
//                switch (choice) {
//                    case 1:
//                        System.out.println("Write a new username : ");
//                        String newUsername = promptConsoleForString();
//
//                        while (newUsername.isEmpty()) {
//                            System.out.println("Error : username can't be empty !");
//                            System.out.println("Write a new username : ");
//                            newUsername = promptConsoleForString();
//                        }
//
//                        return new Settings(newUsername, null, false);
//
//                    case 2:
//                        System.out.println("Write a new password : ");
//                        String newPassword = promptConsoleForString();
//
//                        while (newPassword.isEmpty()) {
//                            System.out.println("Error : password can't be empty !");
//                            System.out.println("Write a new password : ");
//                            newPassword = promptConsoleForString();
//                        }
//
//                        return new Settings(null, newPassword, false);
//
//                    case 3:
//                        System.out.println("Are you sure you want to delete all app data ? (y/n) : ");
//                        String answer = promptConsoleForString();
//                        if (answer.equalsIgnoreCase("y")) {
//                            System.out.println("All app data deleted !");
//                            return new Settings(null, null, true);
//                        } else {
//                            System.out.println("App data not deleted !");
//                            return new Settings(null, null, false);
//                        }
//
//                    default:
//                        System.out.println("Error : you must enter a number between 1 and 3 !");
//                        return null;
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Error : you must enter a number between 1 and 3 !");
//            }
//        } while (true);
//    }
//
//    @Override
//    public SongDetails showSongDetails(String title, String artist, String playlist, String addedDate, String duration) {
//        System.out.println();
//        System.out.println("--------------- Song details ---------------");
//        System.out.println("Title : " + title);
//        System.out.println("Artist : " + artist);
//        System.out.println("Playlist : " + playlist);
//        System.out.println("Added date : " + addedDate);
//        System.out.println("Duration : " + duration);
//        return null;
//    }
//
//    @Override
//    public PlaylistSettings showAndGetPlaylistSettings(String playlistTitle, String playlistOwner) {
//        System.out.println();
//        System.out.println("--------------- Playlist settings ---------------");
//        System.out.println("Playlist title : " + playlistTitle);
//        System.out.println("Playlist owner : " + playlistOwner);
//        System.out.println("Choose the action to do on the playlist (or 0 to cancel) :");
//        System.out.println("1 : Rename playlist");
//        System.out.println("2 : Delete playlist");
//
//        do {
//
//            try {
//                System.out.println("Enter your choice : ");
//                int choice = promptConsoleForInt();
//
//                switch (choice) {
//                    case 1:
//                        System.out.println("Write the new name of the playlist : ");
//                        String newPlaylistName = promptConsoleForString();
//
//                        while (newPlaylistName.isEmpty()) {
//                            System.out.println("Error : the name of the playlist can't be empty !");
//                            System.out.println("Write the new name of the playlist : ");
//                            newPlaylistName = promptConsoleForString();
//                        }
//
//                        return new PlaylistSettings(newPlaylistName, false);
//
//                    case 2:
//                        System.out.println("Are you sure you want to delete the playlist ? (y/n) : ");
//                        String answer = promptConsoleForString();
//                        if (answer.equalsIgnoreCase("y")) {
//                            System.out.println("Playlist deleted !");
//                            return new PlaylistSettings(null, true);
//                        } else {
//                            System.out.println("Playlist not deleted !");
//                            return new PlaylistSettings(null, false);
//                        }
//                    default:
//                        System.out.println("Error : you must enter a number between 1 and 2 !");
//                        return null;
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Error : you must enter a number between 1 and 2 !");
//            }
//        } while (true);
//    }
//
//    @Override
//    public void showMessage(String message) {
//        System.out.println(message);
//    }
//
////    @Override
////    public void toggleFavoritesForCurrentSong() {
////
////    }
//
//    @Override
//    public String promptChooseAddToPlaylist(ArrayList<String> playlistTitleList) {
//        System.out.println("Choose the playlist to add the song (or 0 to cancel) :");
//
//        for (int i = 0; i < playlistTitleList.size(); i++) {
//            System.out.println((i + 1) + " : " + playlistTitleList.get(i));
//        }
//
//        do {
//            try {
//                int choice = promptConsoleForInt();
//
//                if (choice > 0 && choice <= playlistTitleList.size()) {
//                    return playlistTitleList.get(choice - 1);
//                } else {
//                    return null;
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Error : you must enter a number between 1 and " + playlistTitleList.size() + " !");
//            }
//        } while (true);
//    }
//
//    @Override
//    public String promptToCreatePlaylist() {
//        System.out.println("Write the name of the new playlist (or keep empty to cancel): ");
//        String playlistName = promptConsoleForString();
//
//        if (playlistName.isEmpty()) {
//            return null;
//        }
//
//        return playlistName;
//    }
//
//    @Override
//    public String promptChoosePlaylistToDelete(ArrayList<String> playlistTitleList) {
//        System.out.println("Choose the playlist to delete or (0 to cancel) :");
//
//        for (int i = 0; i < playlistTitleList.size(); i++) {
//            System.out.println((i + 1) + " : " + playlistTitleList.get(i));
//        }
//
//        do {
//            try {
//                int choice = promptConsoleForInt();
//
//                if (choice > 0 && choice <= playlistTitleList.size()) {
//                    return playlistTitleList.get(choice - 1);
//                } else {
//                    return null;
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Error : you must enter a number between 1 and " + playlistTitleList.size() + " !");
//            }
//        } while (true);
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//            showMenu();
//        }
//    }
//
//    @Override
//    public void stop() {
//        // Do nothing (because it is a console app)
//    }
//
//    @Override
//    public void setController(Controller c) {
//        controller = c;
//    }
//
//    @Override
//    public File openFile(String fileType, String fileExtension) {
//        System.out.println("Write file name to open (with extension) : ");
//        String fileName = promptConsoleForString();
//
//        if (!fileName.contains(fileExtension)) {
//            System.out.println("Error : the file must be a " + fileExtension + " file !");
//            return null;
//        }
//
//        return new File(fileName);
//    }
//
//    public void showMenu() {
//        System.out.println();
//        System.out.println("--------------- Menu ---------------");
//        System.out.println("0 : Menu bar");
//        System.out.println("1 : Home");
//        System.out.println("2 : Search");
//        System.out.println("3 : Favorites");
//        System.out.println("4 : Pause/play current song");
//        System.out.println("5 : Previous song");
//        System.out.println("6 : Next song");
//        System.out.println("7 : Choose a random song");
//        System.out.println("8 : Loop on current song");
//        System.out.println("9 : Add current song to favorites");
//        System.out.println("10 : Add current song to playlist");
//        System.out.println("11 : Exit");
//
//        boolean entryCorrect;
//
//        do {
//            entryCorrect = true;
//
//            try {
//                System.out.println("Enter your choice : ");
//                int choice = promptConsoleForInt();
//
//                switch (choice) {
//                    case 0:
//                        showMenuBar();
//                        break;
//                    case 1:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.HOME_VIEW));
//                        break;
//                    case 2:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.SEARCH_VIEW));
//                        break;
//                    case 3:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.PLAYLIST_VIEW));
//                        break;
//                    case 4:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.PAUSE_PLAY));
//                        break;
//                    case 5:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.PREVIOUS));
//                        break;
//                    case 6:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.NEXT));
//                        break;
//                    case 7:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.RANDOM));
//                        break;
//                    case 8:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.LOOP));
//                        break;
//                    case 9:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.ADD_TO_FAVORITES));
//                        break;
//                    case 10:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.ADD_TO_PLAYLIST));
//                        break;
//                    case 11:
//                        System.exit(0);
//                        break;
//                    default:
//                        System.out.println("Error : you must enter a number between 0 and 5 !");
//                        entryCorrect = false;
//                        break;
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Error : you must enter a number between 0 and 5 !");
//                entryCorrect = false;
//            }
//        } while (!entryCorrect);
//    }
//
//    private void showMenuBar() {
//        System.out.println();
//        System.out.println("--------------- Menu bar ---------------");
//        System.out.println("0 : Open file");
//        System.out.println("1 : Create a backup");
//        System.out.println("2 : Switch account");
//        System.out.println("3 : Settings");
//        System.out.println("4 : Add to favorites");
//        System.out.println("5 : Remove from favorites");
//        System.out.println("6 : Add to playlist");
//        System.out.println("7 : Remove from playlist");
//        System.out.println("8 : Search for a song");
//        System.out.println("9 : Create playlist");
//        System.out.println("10 : Delete playlist");
//        System.out.println("11 : Edit playlist");
//        System.out.println("12 : Import playlist");
//        System.out.println("13 : Export playlist");
//        System.out.println("14 : Return to menu");
//
//        boolean entryCorrect;
//
//        do {
//            entryCorrect = true;
//
//            try {
//                System.out.println("Enter your choice : ");
//                int choice = promptConsoleForInt();
//
//                switch (choice) {
//                    case 0:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.OPEN_SONG));
//                        break;
//                    case 1:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.CREATE_BACKUP));
//                        break;
//                    case 2:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.SWITCH_ACCOUNT));
//                        break;
//                    case 3:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.SETTINGS));
//                        break;
//                    case 4:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.ADD_TO_FAVORITES));
//                        break;
//                    case 5:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.REMOVE_SONG_FROM_FAVORITES));
//                        break;
//                    case 6:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.ADD_TO_PLAYLIST));
//                        break;
//                    case 7:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.REMOVE_SONG_FROM_PLAYLIST));
//                        break;
//                    case 8:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.SEARCH_VIEW));
//                        break;
//                    case 9:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.CREATE_PLAYLIST));
//                        break;
//                    case 10:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.DELETE_PLAYLIST));
//                        break;
//                    case 11:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.PLAYLIST_SETTINGS));
//                        break;
//                    case 12:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.IMPORT_PLAYLIST));
//                        break;
//                    case 13:
//                        controller.actionPerformed(new ActionEvent(this, 0, ControllerActions.EXPORT_PLAYLIST));
//                        break;
//                    case 14:
//                        break;
//                    default:
//                        System.out.println("Error : you must enter a number between 0 and 14 !");
//                        entryCorrect = false;
//                        break;
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Error : you must enter a number between 0 and 14 !");
//                entryCorrect = false;
//            }
//        } while (!entryCorrect);
//    }
//
//    public void showCurrentSong() {
//
//    }
//
//    private String promptConsoleForString() {
//        return scanner.nextLine();
//    }
//
//    private float promptConsoleForFloat() {
//        String txt = scanner.nextLine();
//        float nb = Float.parseFloat(txt); // !!!
//        return nb;
//    }
//
//    private int promptConsoleForInt() {
//        String txt = scanner.nextLine();
//        int nb = Integer.parseInt(txt); // !!!
//        return nb;
//    }
//
//    private boolean promptConsoleForBoolean() {
//        String txt = scanner.nextLine();
//        boolean b = Boolean.parseBoolean(txt); // !!!
//        return b;
//    }
//}
