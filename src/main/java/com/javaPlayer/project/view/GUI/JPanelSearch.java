package com.javaPlayer.project.view.GUI;

import com.javaPlayer.project.controller.Controller;
import com.javaPlayer.project.controller.ControllerActions;
import com.javaPlayer.project.model.entity.Song;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JPanelSearch extends JPanel {
    public JPanel mainPanel;
    private JTextField searchTextField;
    private JLabel searchTitleLabel;
    private JPanel searchTitlePanel;
    private JPanel searchPanel;
    private JButton searchButton;
    private JPanel searchButtonPanel;
    private JPanel searchResultsPanel;
    private JScrollPane searchResultsScrollPane;
    private JTable songResultsTable;

    private Controller controller;

    public JPanelSearch() {
        searchResultsScrollPane.setBorder(BorderFactory.createEmptyBorder());
    }

    void setController(Controller c) {
        this.controller = c;

        searchButton.setActionCommand(ControllerActions.SEARCH_SONG);
        searchButton.addActionListener(c);
        searchTextField.setActionCommand(ControllerActions.SEARCH_SONG);
        searchTextField.addActionListener(c);

        //add a listenner at the searchTextField
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
                if(controller != null) {
                    ActionEvent actionEvent = new ActionEvent(searchTextField, ActionEvent.ACTION_PERFORMED, ControllerActions.SEARCH_SONG);
                    controller.actionPerformed(actionEvent);
                }
            }
        });

        // Sélectionner une chanson
        songResultsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = songResultsTable.getSelectedRow();
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

    // Mettre à jour les résultats dans le tableau
    public void updateResults(ArrayList<Song> songList) {
        if (songList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune chanson trouvée.", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }

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

    public String getSearchText() {
        return searchTextField.getText().trim();//get the text and delete the blank space
    }

    // Créer un composant personnalisé pour la table des chansons
    private void createUIComponents() {
        songResultsTable = new JTablePlaylist(new DefaultTableModel());
    }
}
