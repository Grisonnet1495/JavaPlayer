package com.javaPlayer.project;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.model.FilePathNames;
import com.javaPlayer.project.model.authentication.FileAuthenticator;
import com.javaPlayer.project.model.dao.DAOConfig;
import com.javaPlayer.project.view.GUI.JFrameMainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            DAOConfig daoConfig = new DAOConfig(FilePathNames.CONFIG);

            MainController mainController = new MainController(new JFrameMainWindow(), daoConfig, new FileAuthenticator(daoConfig.getConfig("userFile")));
            mainController.run();
        });
    }
}
