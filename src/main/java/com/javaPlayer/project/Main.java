package com.javaPlayer.project;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.model.authentication.FileAuthenticator;
import com.javaPlayer.project.view.GUI.JFrameMainWindow;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {

            String configPath = "config.properties";
            File file = new File(configPath);
            Properties config = new Properties();
            try
            {
                if(file.createNewFile())
                {
                    System.out.println("File created");
                    config.setProperty("users", "users.properties");
                    try(FileOutputStream fot = new FileOutputStream(file))
                    {
                        config.store(fot, "Liste des fichiers");
                    }
                }else{
                    try(FileInputStream fis = new FileInputStream(file))
                    {
                        config.load(fis);
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println("File Creation Failed");
            }
            String users = config.getProperty("users");
            if (users == null) {
                System.out.println("Fichier utilisateur non défini dans config.properties.");
                return;
            }
            MainController mainController = new MainController(new JFrameMainWindow(), new FileAuthenticator(users));
            mainController.run();
        });
    }
}

