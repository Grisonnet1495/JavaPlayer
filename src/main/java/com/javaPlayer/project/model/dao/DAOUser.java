package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.entity.User;
import com.javaPlayer.project.model.exception.UserException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DAOUser implements IDAOUser, Serializable {
    private String usersFilename; // Filename of the file containing all users
    private ArrayList<User> usersList; // All users list

    private User currentUser; // Current user

    public DAOUser(String usersFilename) {
        this.usersFilename = usersFilename;

        // Create the users file if it doesn't exist
        if (!new File(usersFilename).exists()) {
            usersList = new ArrayList<>();
            saveUsersToFile();
        }

        loadUsersFromFile();
    }

    @Override
    public void loadUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFilename))) {
            usersList = (ArrayList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new UserException("Cannot load user file : " + e.getMessage());
        }
    }

    @Override
    public void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFilename))) {
            oos.writeObject(usersList);
        } catch (IOException e) {
            throw new UserException("Cannot save user file : " + e.getMessage());
        }
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void addUser(User newUser) {
        if (usersList.stream().anyMatch(user -> user.getPseudo().equalsIgnoreCase(newUser.getPseudo()))) {
            throw new UserException("This user pseudo already exists !");
        }

        // Find an new id
        int id = 1;
        Set<Integer> usedIds = new HashSet<>();

        for (User u : usersList) {
            usedIds.add(u.getId());
        }

        while (usedIds.contains(id)) {
            id++;
        }

        newUser.setId(id);

        usersList.add(newUser);
        saveUsersToFile();
    }

    @Override
    public void removeUserById(int userId) {
        boolean isUserRemoved = usersList.removeIf(user -> user.getId() == userId);

        if (!isUserRemoved) {
            throw new UserException("User does not exist!");
        }

        saveUsersToFile();
    }

    @Override
    public ArrayList<User> getUsersList() {
        return usersList;
    }

    @Override
    public User getUserById(int userId) {
        for (User u : usersList) {
            if (u.getId() == userId) {
                return u;
            }
        }

        return null;
    }

    @Override
    public User getUserByPseudo(String pseudo) {
        for (User u : usersList) {
            if (u.getPseudo().equalsIgnoreCase(pseudo)) {
                return u;
            }
        }

        return null;
    }

    @Override
    public void updateUserById(int userId, String newPseudo, String newPassword) {
        if (newPseudo == null || newPseudo.trim().isEmpty()) {
            throw new UserException("User pseudo cannot be empty !");
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new UserException("Password cannot be empty !");
        }

        User userToUpdate = getUserById(userId);

        if (userToUpdate == null) {
            throw new UserException("User not found !");
        }

        if (!userToUpdate.getPseudo().equals(newPseudo) && usersList.stream().anyMatch(user -> user.getPseudo().equalsIgnoreCase(newPseudo))) {
            throw new UserException("User pseudo already taken !");
        }

        userToUpdate.setPseudo(newPseudo);
        userToUpdate.setPassword(newPassword);

        saveUsersToFile();
    }
}
