package com.javaPlayer.project.model.authentication;

import java.io.*;
import java.util.Properties;

public class FileAuthenticator extends Authenticator {

    private final Properties users = new Properties();
    private final String filename;

    public FileAuthenticator(String fileName) {
        this.filename = fileName;
        loadUsers();
    }

    @Override
    public void loadUsers() {
        try (FileInputStream fis = new FileInputStream(filename)) {
            users.load(fis);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    @Override
    public void saveUsers() {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            users.store(fos, "User list");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    @Override
    public void addUsers(String pseudo, String password) {
        if (!users.containsKey(pseudo)) {
            users.put(pseudo, password);
            saveUsers();
            System.out.println("User added successfully.");
        } else {
            System.out.println("User already exists.");
        }
    }

    @Override
    public void removeUser(String pseudo) {
        if (users.containsKey(pseudo)) {
            users.remove(pseudo);
            saveUsers(); // Update file
            System.out.println("User removed successfully.");
        } else {
            System.out.println("Cannot find user.");
        }
    }

    @Override
    public boolean isLoginExists(String pseudo) {
        return users.containsKey(pseudo);
    }

    @Override
    public String getPassword(String pseudo) {
        return (String)users.getOrDefault(pseudo, "");
    }
}
