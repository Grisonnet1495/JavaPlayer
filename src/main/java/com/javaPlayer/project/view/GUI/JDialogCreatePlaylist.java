package com.javaPlayer.project.view.GUI;

import javax.swing.*;
import java.awt.*;

public class JDialogCreatePlaylist extends JDialog {
    private JPanel mainPanel;
    private JButton cancelButton;
    private JButton createButton;
    private JTextField playlistNameTextField;
    private String newPlaylistName = null;
    private boolean isCreatingPlaylist = false;

    public JDialogCreatePlaylist(JFrame parent, boolean modal) {
        super(parent, "Enter the new playlist name", modal);
        this.setContentPane(mainPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Set the properties of the dialog box
        this.setSize(300, 150);
        this.setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

        // Add action listeners
        createButton.addActionListener(e -> {
            newPlaylistName = playlistNameTextField.getText();
            isCreatingPlaylist = true;
            this.dispose();
        });

        cancelButton.addActionListener(e -> {
            this.dispose();
        });
    }

    public String getNewPlaylistName() {
        return newPlaylistName;
    }

    public boolean isCreatingPlaylist() {
        return isCreatingPlaylist;
    }
}
