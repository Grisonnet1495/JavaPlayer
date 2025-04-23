package com.javaPlayer.project.view.GUI;

import com.javaPlayer.project.controller.MainController;
import com.javaPlayer.project.controller.MainControllerActions;
import com.javaPlayer.project.model.entity.Song;

import javax.swing.*;
import java.util.ArrayList;

public class JPanelSearch extends JPanel {
    public JPanel mainPanel;
    private JTextField searchTextField;
    private JTable resultTable;
    private JLabel searchTitleLabel;
    private JPanel searchTitlePanel;
    private JPanel searchPanel;
    private JButton searchButton;
    private JPanel searchButtonPanel;

    private MainController controller;

    public JPanelSearch() {
        // Nothing to do
    }

    void updateResults(ArrayList<Song> songList) {
        // Display results
    }

    void setController(MainController c) {
        searchButton.setActionCommand(MainControllerActions.SEARCH_SONG);
        searchButton.addActionListener(c);
        searchTextField.setActionCommand(MainControllerActions.SEARCH_SONG);
        searchTextField.addActionListener(c);

        this.controller = c;
    }
}
