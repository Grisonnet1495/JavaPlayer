package com.javaPlayer.project;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.model.authentication.FileAuthenticator;
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
            MainController mainController = new MainController(new JFrameMainWindow(), new FileAuthenticator());
            mainController.run();
        });
    }
}
