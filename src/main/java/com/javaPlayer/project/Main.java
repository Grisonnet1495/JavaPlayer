package com.javaPlayer.project;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.javaPlayer.project.controller.Controller;
import com.javaPlayer.project.model.dao.DAOPlaylist;
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
            Controller controller = new Controller(new JFrameMainWindow(), new DAOPlaylist());
            controller.run();
        });
    }
}
