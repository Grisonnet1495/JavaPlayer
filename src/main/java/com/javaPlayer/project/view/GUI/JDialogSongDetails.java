package com.javaPlayer.project.view.GUI;

import javax.swing.*;
import java.awt.*;

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
    private JLabel songDurationTitleLabel;
    private JLabel songDurationLabel;

    public JDialogSongDetails(JFrame parent, boolean modal) {
        super(parent, "Song details", modal);
        this.setContentPane(mainPanel);

        // Set the properties of the dialog box
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(500, 250);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
    }

    public void setSongTitle(String title) {
        songTitleLabel.setText(title);
    }

    public void setSongArtist(String artist) {
        songArtistLabel.setText(artist);
    }

    public void setSongPlaylist(String playlist) {
        songPlaylistLabel.setText(playlist);
    }

    public void setSongAddedDate(String date) {
        songAddedDateLabel.setText(date);
    }

    public void setSongDuration(String length) {
        songDurationLabel.setText(length);
    }
}
