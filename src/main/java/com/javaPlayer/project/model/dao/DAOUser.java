package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.model.entity.User;

import java.util.ArrayList;

public class DAOUser {
    private User currentUser;
    private ArrayList<User> UserList;

    public DAOUser(String user) {
        loadUserFromFile(user);
    }

    public void saveCurrentUserToFile() {
        // Code to save all user data in a file
    }

    public void loadUserFromFile(String user) {
        // Code to load all user data from a file
    }
}
