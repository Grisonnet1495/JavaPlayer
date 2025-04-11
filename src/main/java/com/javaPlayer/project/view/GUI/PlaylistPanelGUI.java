package com.javaPlayer.project.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PlaylistPanelGUI extends JPanel {
    public JPanel mainPanel;
    private JScrollBar scrollBar1;
    private JTable songTable;
    private JPanel PlaylistInfoPanel;
    private JPanel songTableJPanel;
    private JPanel playlistTilePanel;
    private JLabel PlaylistTitleLabel;
    private JPanel playlistIconOutPanel;
    public JButton playlistSettingsButton;

    PlaylistPanelGUI() {
        playlistSettingsButton.addActionListener(this::actionPerformed);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playlistSettingsButton) {
            // Create the settings dialog box
            JDialog playlistSettingsDialog = new JDialog(((JFrame) SwingUtilities.getWindowAncestor(this)), true);
            playlistSettingsDialog.setTitle("Playlist settings");

            // Set the properties of the dialog box
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            playlistSettingsDialog.setSize(new Dimension(500, 300));
            playlistSettingsDialog.setResizable(false);

            int x = (screenSize.width - playlistSettingsDialog.getWidth()) / 2;
            int y = (screenSize.height - playlistSettingsDialog.getHeight()) / 2;
            playlistSettingsDialog.setLocation(x, y);

            playlistSettingsDialog.setContentPane(new PlaylistSettingsPanelGUI().mainPanel);
            playlistSettingsDialog.setVisible(true);
            playlistSettingsDialog.dispose();
        }
    }
}
