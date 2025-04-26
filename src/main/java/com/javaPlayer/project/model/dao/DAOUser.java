package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.FilePathNames;
import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.model.entity.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class DAOUser {
    private ArrayList<User> UserList;

    public DAOUser() {
        DAOConfig daoConfig = new DAOConfig(FilePathNames.CONFIG);

        if (!daoConfig.isConfigExists("userFile")) {
            daoConfig.addConfig("userFile", FilePathNames.USERS);
        }
        loadUserFromFile();
    }

    public void saveCurrentUserToFile() {
        // Code to save all user data in a file
    }

    public void loadUserFromFile() {
        // Code to load all user data from a file
        try{
            FileInputStream fis = new FileInputStream(FilePathNames.USERS);
            ObjectInputStream ois = new ObjectInputStream(fis);

            UserList = (ArrayList<User>)ois.readObject();
            ois.close();

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
