package com.javaPlayer.project.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JDialogAddToPlaylist extends JDialog {
    public JPanel mainPanel;
    private JComboBox playlistComboBox;
    private JButton addButton;
    private JButton cancelButton;
    private String selectedPlaylist = null;
    private boolean isAddingSongToPlaylist = false;

    public JDialogAddToPlaylist(JFrame parent, boolean modal, ArrayList<String> playlistTitleList ) {
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
        addButton.addActionListener(e -> {
            selectedPlaylist = (String) playlistComboBox.getSelectedItem();
            isAddingSongToPlaylist = true;
            this.dispose();
        });

        cancelButton.addActionListener(e -> {
            this.dispose();
        });

        for (String playlistTitle : playlistTitleList) {
            playlistComboBox.addItem(playlistTitle);
        }
    }

    public String getSelectedPlaylist() {
        return selectedPlaylist;
    }

    public boolean isAddingSongToPlaylist() {
        return isAddingSongToPlaylist;
    }
}
