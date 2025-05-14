package com.javaPlayer.project.view.GUI;

import com.javaPlayer.project.model.entity.Playlist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JDialogEditPlaylist extends JDialog {
    private JPanel mainPanel;
    private JButton editButton;
    private JButton cancelButton;
    private JComboBox playlistComboBox;
    private String selectedPlaylist = null;

    public JDialogEditPlaylist(JFrame parent, boolean modal, ArrayList<Playlist> playlistList) {
        super(parent, "Select playlist", modal);
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
        editButton.addActionListener(e -> {
            selectedPlaylist = (String) playlistComboBox.getSelectedItem();
            this.dispose();
        });

        cancelButton.addActionListener(e -> {
            this.dispose();
        });

        for (Playlist p : playlistList) {
            playlistComboBox.addItem(p.getTitle());
        }
    }

    public String getSelectedPlaylist() {
        return selectedPlaylist;
    }
}
