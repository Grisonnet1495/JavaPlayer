package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.entity.User;

import java.util.ArrayList;

public interface IDAOUser {
    // Backup methods
    public void loadUsersFromFile();
    public void saveUsersToFile();

    // Set and get methods
    public void setCurrentUser(User user);
    public User getCurrentUser();

    // CRUD methods
    public void addUser(User newUser);
    public void removeUserById(int userId);
    public ArrayList<User> getUsersList();
    public User getUserById(int userId);
    public User getUserByPseudo(String pseudo);
    public void updateUserById(int userId, String newPseudo, String newPassword);
}
