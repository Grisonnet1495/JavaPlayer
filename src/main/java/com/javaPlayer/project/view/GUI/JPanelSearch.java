package com.javaPlayer.project.view.GUI;

import com.javaPlayer.project.controller.Controller;
import com.javaPlayer.project.controller.ControllerActions;
import com.javaPlayer.project.model.entity.Song;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JPanelSearch extends JPanel {
    public JPanel mainPanel;
    private JTextField searchTextField;
    private JLabel searchTitleLabel;
    private JPanel searchTitlePanel;
    private JPanel searchPanel;
    private JPanel searchResultsPanel;
    private JScrollPane searchResultsScrollPane;
    private JTable songResultsTable;

    private Controller controller;

    public JPanelSearch() {
        searchResultsScrollPane.setBorder(BorderFactory.createEmptyBorder());
    }

    void setController(Controller c) {
        this.controller = c;

        // Add a listener at the searchTextField
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                notifyChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                notifyChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                notifyChange();
            }

            public void notifyChange() {
                if (controller != null) {
                    String searchText = searchTextField.getText().trim();
                    ActionEvent actionEvent = new ActionEvent(searchText, ActionEvent.ACTION_PERFORMED, ControllerActions.SEARCH_SONG);
                    controller.actionPerformed(actionEvent);
                }
            }
        });

        // Add a listener for the song selection
        songResultsTable.getSelectionModel().addListSelectionListener(e -> {
            // If it isn't just a screen update
            if (!e.getValueIsAdjusting()) {
                int selectedRow = songResultsTable.getSelectedRow();

                // If a row is selected
                if (selectedRow != -1) {
                    int songId = (int) songResultsTable.getValueAt(selectedRow, 0);

                    if (controller != null) {
                        ActionEvent event = new ActionEvent(songId, ActionEvent.ACTION_PERFORMED, ControllerActions.PLAY_SELECTED_SONG);
                        controller.actionPerformed(event);
                    }
                }
            }
        });
    }

    // Update results table
    public void updateResults(ArrayList<Song> songList) {
        String[] columnTitles = {"N°", "Title", "Artist", "Genre", "Duration", "Added date"};
        Object[][] tableData = new Object[songList.size()][columnTitles.length];

        for (int i = 0; i < songList.size(); i++) {
            tableData[i][0] = songList.get(i).getId();
            tableData[i][1] = songList.get(i).getTitle();
            tableData[i][2] = songList.get(i).getArtist();
            tableData[i][3] = songList.get(i).getGenre();
            tableData[i][4] = songList.get(i).getFormattedDuration();
            tableData[i][5] = songList.get(i).getAddedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnTitles);
        songResultsTable.setModel(tableModel);
    }

    // Create a custom component for the song results table
    private void createUIComponents() {
        songResultsTable = new JTablePlaylist(new DefaultTableModel());
    }
}
