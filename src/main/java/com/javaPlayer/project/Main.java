package com.javaPlayer.project;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.javaPlayer.project.controller.Controller;
import com.javaPlayer.project.model.dao.DAOPlaylist;
import com.javaPlayer.project.model.dao.DAOUser;
import com.javaPlayer.project.model.dao.IDAOConfig;
import com.javaPlayer.project.model.player.MusicPlayer;
import com.javaPlayer.project.utils.Constants;
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
            IDAOConfig daoConfig = new DAOConfig(Constants.CONFIG_FILENAME);

            Controller controller = new Controller(new JFrameMainWindow(),
                    new FileAuthenticator(daoConfig.getConfig(Constants.USER_PASSWORDS_CONFIG_KEY)),
                    new DAOUser(daoConfig.getConfig(Constants.USERS_CONFIG_KEY)),
                    new DAOPlaylist(daoConfig.getConfig(Constants.USER_PLAYLISTS_CONFIG_KEY)),
                    new MusicPlayer());
            controller.run();
        });
    }
}
