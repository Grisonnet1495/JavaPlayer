package com.javaPlayer.project.view.GUI;

import javax.swing.*;

public class JDialogSongDetails extends JDialog {
    public JPanel mainPanel;
    private JLabel songTitleTitleLabel;
    private JLabel songTitleLabel;
    private JLabel songArtistTitleLabel;
    private JLabel songArtistLabel;
    private JLabel songPlaylistTitleLabel;
    private JLabel songPlaylistLabel;
    private JLabel songAddedDateTitleLabel;
    private JLabel songAddedDateLabel;
    private JLabel songLengthTitleLabel;
    private JLabel songLengthLabel;

    public JDialogSongDetails(JFrame parent, boolean modal) {
        super(parent, "Song details", modal);
        this.setContentPane(mainPanel);
//        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(500, 250);
    }
}
