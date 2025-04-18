package com.javaPlayer.project.model.authentication;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileAuthenticator extends Authenticator {

    private final Properties users = new Properties();
    private final String filename;

    public FileAuthenticator(String fileName) {
        this.filename = fileName;
        uploadUsers();
    }

    @Override
    public void uploadUsers() {
        try{
            users.load(new FileInputStream(filename));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        catch(IOException e)
        {
            System.out.println("IO Exception");
        }
    }

    @Override
    public void saveUsers(String fileName) {
        try
        {
            users.store(new FileOutputStream(fileName), "Liste des utilisateurs");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        catch(IOException e)
        {
            System.out.println("IO Exception");
        }
    }

    @Override
    public void addUsers(String pseudo, String password) {
        if (!users.containsKey(pseudo)) {
            users.put(pseudo, password);
            saveUsers(filename);
        } else {
            System.out.println("Utilisateur déjà existant !");
        }
    }

    @Override
    public void removeUser(String pseudo) {
        if (users.containsKey(pseudo)) {
            users.remove(pseudo);
            saveUsers(filename);//mettre à jour le fichier
            System.out.println("Utilisateur supprimé avec succès.");
        } else {
            System.out.println("Utilisateur introuvable.");
        }
    }

    @Override
    protected boolean isLoginExists(String pseudo) {
        return users.containsKey(pseudo);
    }

    @Override
    protected String getPassword(String pseudo) {
        return (String)users.getOrDefault(pseudo, "");
    }
}
