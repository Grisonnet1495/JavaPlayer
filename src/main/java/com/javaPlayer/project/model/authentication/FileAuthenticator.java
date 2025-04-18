package com.javaPlayer.project.model.authentication;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileAuthenticator extends Authenticator {

    private final String filePath = "users.txt";
    private final Map<String, String> utilisateurs = new HashMap<>();

    public FileAuthenticator() {
        uploadUsers();
    }

    @Override
    public void uploadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parties = line.split(":");
                if (parties.length == 2) {
                    utilisateurs.put(parties[0], parties[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

    @Override
    public void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : utilisateurs.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue()); // ecrire dans le fichier la map
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erreur d'écriture : " + e.getMessage());
        }
    }

    @Override
    public void addUsers(String pseudo, String password) {
        if (!utilisateurs.containsKey(pseudo)) {
            utilisateurs.put(pseudo, password);
            saveUsers();
        } else {
            System.out.println("Utilisateur déjà existant !");
        }
    }

    @Override
    public void removeUser(String pseudo) {
        if (utilisateurs.containsKey(pseudo)) {
            utilisateurs.remove(pseudo);
            saveUsers();//mettre à jour le fichier
            System.out.println("Utilisateur supprimé avec succès.");
        } else {
            System.out.println("Utilisateur introuvable.");
        }
    }

    @Override
    protected boolean isLoginExists(String pseudo) {
        return utilisateurs.containsKey(pseudo);
    }

    @Override
    protected String getPassword(String pseudo) {
        return utilisateurs.getOrDefault(pseudo, "");
    }
}
