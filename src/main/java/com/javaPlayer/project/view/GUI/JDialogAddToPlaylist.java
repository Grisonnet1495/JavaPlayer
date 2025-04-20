package com.javaPlayer.project.view.GUI;

import javax.swing.*;

public class JDialogAddToPlaylist extends JDialog {
    public JPanel mainPanel;
    private JComboBox comboBox1;
    private JButton addButton;
    private JButton cancelButton;

    public JDialogAddToPlaylist(JFrame parent, boolean modal) {
        super(parent, "Add to playlist", modal);
        this.setContentPane(mainPanel);
//        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(300, 150);
        this.setResizable(false);
    }
}
