package com.javaPlayer.project.model.authentication;

import com.javaPlayer.project.model.exception.AuthenticatorException;

import java.io.*;
import java.util.Properties;

public class FileAuthenticator extends Authenticator {
    private String usersPasswordsFilename; // Filename of the file containing user passwords
    private Properties usersPasswords = new Properties(); // User passwords

    public FileAuthenticator(String fileName) {
        this.usersPasswordsFilename = fileName;

        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new AuthenticatorException("Cannot create file", e);
            }
        }

        loadUsers();
    }

    @Override
    public void loadUsers() {
        try (FileInputStream fis = new FileInputStream(usersPasswordsFilename)) {
            usersPasswords.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUsers() {
        try (FileOutputStream fos = new FileOutputStream(usersPasswordsFilename)) {
            usersPasswords.store(fos, "User passwords");
        } catch (IOException e) {
            throw new AuthenticatorException("Cannot save users", e);
        }
    }

    @Override
    public void addUsers(String pseudo, String password) {
        if (usersPasswords.containsKey(pseudo)) {
            throw new AuthenticatorException("User already exists.");
        }

        usersPasswords.put(pseudo, password);
        saveUsers();
    }

    @Override
    public void removeUser(String pseudo) {
        if (!usersPasswords.containsKey(pseudo)) {
            throw new AuthenticatorException("User does not exist.");
        }

        usersPasswords.remove(pseudo);
        saveUsers();
    }

    @Override
    public void changeUserPseudo(String oldPseudo, String newPseudo) {
        if (newPseudo == null || newPseudo.trim().isEmpty()) {
            throw new AuthenticatorException("New pseudo cannot be empty.");
        }

        if (oldPseudo.equals(newPseudo)) {
            return;
        }

        if (!usersPasswords.containsKey(oldPseudo)) {
            throw new AuthenticatorException("Pseudo to change doesn't exist.");
        }

        if (usersPasswords.containsKey(newPseudo)) {
            throw new AuthenticatorException("New pseudo already exists.");
        }

        String password = usersPasswords.getProperty(oldPseudo);
        usersPasswords.remove(oldPseudo);
        usersPasswords.setProperty(newPseudo, password);
        saveUsers();
    }

    @Override
    public void changeUserPassword(String pseudo, String newPassword) {
        if (!usersPasswords.containsKey(pseudo)) {
            throw new AuthenticatorException("Pseudo doesn't exist.");
        }

        usersPasswords.setProperty(pseudo, newPassword);
        saveUsers();
    }

    @Override
    public boolean isLoginExists(String pseudo) {
        return usersPasswords.containsKey(pseudo);
    }

    @Override
    public String getPassword(String pseudo) {
        return (String) usersPasswords.getOrDefault(pseudo, "");
    }
}
