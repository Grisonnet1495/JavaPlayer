package com.javaPlayer.project.view.GUI;

import javax.swing.*;
import java.awt.*;

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
    private JCheckBox acceptConsequencesCheckBox;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel saveAndCancelPanel;
    private JPanel contentPanel;
    private JPanel playlistNamePanel;
    private JPanel playlistOwnerPanel;
    private boolean isSaving = false;
    private boolean isDeletingPlaylist = false;
    private String playlistName = null;


    public JDialogPlaylistSettings(JFrame parent, boolean modal, String playlistTitle, String playlistOwner, boolean canPlaylistBeRenamed, boolean canPlaylistBeDeleted) {
        super(parent, "Playlist settings", modal);
        this.setContentPane(mainPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Set the properties of the dialog box
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(new Dimension(500, 390));
        this.setResizable(false);

        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

        if (!canPlaylistBeRenamed) {
            playlistNameTextField.setEnabled(false);
        }

        if (!canPlaylistBeDeleted) {
            deletePlaylistButton.setEnabled(false);
            acceptConsequencesCheckBox.setEnabled(false);
        }

        // Add action listeners
        saveButton.addActionListener(e -> {
            isSaving = true;
            playlistName = playlistNameTextField.getText();
            this.dispose();
        });

        cancelButton.addActionListener(e -> {
            this.dispose();
        });

        deletePlaylistButton.addActionListener(e -> {
            if (acceptConsequencesCheckBox.isSelected()) {
                isDeletingPlaylist = true;
                deletePlaylistButton.setText("Please save to confirm...");
                acceptConsequencesCheckBox.setEnabled(false);
                playlistNameTextField.setEnabled(false);
            }
        });

        if (playlistTitle != null) playlistNameTextField.setText(playlistTitle);
        if (playlistOwner != null) playlistOwnerLabel.setText(playlistOwner);
    }

    public boolean isSaving() {
        return isSaving;
    }

    public boolean isDeletingPlaylist() {
        return isDeletingPlaylist;
    }

    public String getPlaylistName() {
        return playlistName;
    }
}
