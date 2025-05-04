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

    public void changePseudo(String pseudo, String newPseudo) {
        if (!usersPasswords.containsKey(pseudo)) {
            throw new AuthenticatorException("Pseudo to change does not exist.");
        }

        if (usersPasswords.containsKey(newPseudo)) {
            throw new AuthenticatorException("New pseudo already exists.");
        }

        String password = usersPasswords.getProperty(pseudo);
        usersPasswords.remove(pseudo);
        usersPasswords.setProperty(newPseudo, password);
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
