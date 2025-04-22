package com.javaPlayer.project.view.GUI;

import javax.swing.*;
import java.awt.*;

public class JDialogDeletePlaylist extends JDialog {
    private JPanel mainPanel;
    private JComboBox playlistComboBox;
    private JButton cancelButton;
    private JButton deleteButton;
    private String selectedPlaylist = null;

    public JDialogDeletePlaylist(JFrame parent, boolean modal) {
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
        deleteButton.addActionListener(e -> {
            selectedPlaylist = (String) playlistComboBox.getSelectedItem();
            this.dispose();
        });

        cancelButton.addActionListener(e -> {
            this.dispose();
        });
    }

    public String getSelectedPlaylist() {
        return selectedPlaylist;
    }
}
