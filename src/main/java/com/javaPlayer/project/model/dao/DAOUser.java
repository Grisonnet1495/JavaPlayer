package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.entity.User;
import com.javaPlayer.project.model.exception.UserException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DAOUser implements Serializable {
    private String usersFilename; // Filename of the file containing all users
    private ArrayList<User> usersList; // All users list

    private User currentUser; // Current user

    public DAOUser(String usersFilename) {
        this.usersFilename = usersFilename;

        // If the
        if (!new File(usersFilename).exists()) {
            usersList = new ArrayList<>();
            saveUsersToFile();
        }

        loadUsersFromFile();
    }

    public void loadUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFilename))) {
            usersList = (ArrayList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new UserException("Cannot load user file", e);
        }
    }

    public void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFilename))) {
            oos.writeObject(usersList);
        } catch (IOException e) {
            throw new UserException("Cannot save user file", e);
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

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

        usersList.add(newUser);
    }

    public void removeUser(User userToRemove) {
        if (usersList.stream().noneMatch(user -> user.getId() == userToRemove.getId())) {
            throw new UserException("User does not exist !");
        }

        usersList.removeIf(user -> user.getId() == userToRemove.getId());
    }

    public ArrayList<User> getUsersList() {
        return usersList;
    }

    public User getUserById(int userId) {
        for (User u : usersList) {
            if (u.getId() == userId) {
                return u;
            }
        }
        return null;
    }

    public User getUserByPseudo(String pseudo) {
        for (User u : usersList) {
            if (u.getPseudo().equalsIgnoreCase(pseudo)) {
                return u;
            }
        }
        return null;
    }

    public void updateUserById(int userId, User newUser) {
        if (newUser.getPseudo() == null || newUser.getPseudo().trim().isEmpty()) {
            throw new UserException("User pseudo cannot be empty !");
        }

        if (newUser.getPassword() == null || newUser.getPassword().trim().isEmpty()) {
            throw new UserException("Password cannot be empty !");
        }

        if (usersList.stream().anyMatch(user -> user.getPseudo().equalsIgnoreCase(newUser.getPseudo()))) {
            throw new UserException("User pseudo already taken !");
        }

        User user = getUserById(userId);

        if (user == null) {
            throw new UserException("User not found !");
        }

        user.setPseudo(user.getPseudo());
        user.setPassword(user.getPassword());
    }
}
