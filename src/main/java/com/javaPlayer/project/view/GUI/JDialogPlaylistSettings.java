package com.javaPlayer.project.view.GUI;

import javax.swing.*;

public class JDialogPlaylistSettings extends JDialog {
    protected JPanel mainPanel;
    private JTextField playlistNameTextField;
    private JButton deletePlaylistButton;
    private JLabel playlistNameTitleLabel;
    private JLabel playlistOwnerTitleLabel;
    private JLabel playlistOwnerLabel;
    private JLabel deletePlaylistTitleLabel;
    private JLabel deletePlaylistInfoUpLabel;
    private JPanel deletePlaylistPanel;

    public JDialogPlaylistSettings(JFrame parent, boolean modal) {
        super(parent, "Playlist settings", modal);
        this.setContentPane(mainPanel);
//        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
