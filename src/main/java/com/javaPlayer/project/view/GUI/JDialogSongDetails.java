package com.javaPlayer.project.view.GUI;

import javax.swing.*;
import java.awt.*;

public class JDialogSongDetails extends JDialog {
    public JPanel mainPanel;
    private JLabel songTitleTitleLabel;
    private JLabel songArtistTitleLabel;
    private JLabel songPlaylistTitleLabel;
    private JLabel songPlaylistLabel;
    private JLabel songAddedDateTitleLabel;
    private JLabel songAddedDateLabel;
    private JLabel songDurationTitleLabel;
    private JLabel songDurationLabel;
    private JTextField songTitleTextField;
    private JTextField songArtistTextField;

    public JDialogSongDetails(JFrame parent, boolean modal) {
        super(parent, "Song details", modal);
        this.setContentPane(mainPanel);

        // Set the properties of the dialog box
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(400, 250);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
    }

    public void updateSongDetails(String title, String artist, String playlist, String date, String length) {
        if (title != null) songTitleTextField.setText(title);
        if (artist != null) songArtistTextField.setText(artist);
        if (playlist != null) songPlaylistLabel.setText(playlist);
        if (date != null) songAddedDateLabel.setText(date);
        if (length != null) songDurationLabel.setText(length);
    }

    public String getSongTitle() {
        return songTitleTextField.getText();
    }

    public String getSongArtist() {
        return songArtistTextField.getText();
    }
}
