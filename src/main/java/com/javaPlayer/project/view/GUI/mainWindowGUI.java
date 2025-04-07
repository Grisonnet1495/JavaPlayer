package com.javaPlayer.project.view.GUI;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainWindowGUI extends JFrame implements ActionListener {
    // Main panel
    private JPanel mainPanel;
    private JMenuBar menuBar;

    // Left Menu Panel
    private JButton homeButton;
    private JButton searchButton;
    private JButton favoritesButton;
    private JPanel leftMenuPanel;

    // Song panel
    private JPanel songPanel;
    private JScrollPane contentScrollPane;
    private JSlider timeSlider;
    private JPanel songTimeActionsPanel;
    private JPanel songActionsPanel;
    private JButton pausePlayButton;
    private JButton previousButton;
    private JButton nextButton;
    private JButton randomButton;
    private JButton loopButton;
    private JLabel elapsedTimeLabel;
    private JLabel remainingTimeLabel;
    private JLabel playlistsLabel;
    private JButton songIconButton;
    private JLabel songTitleLabel;
    private JLabel songArtistLabel;
    private JButton volumeButton;
    private JPanel volumePanel;
    private JPanel songIconInfoPanel;
    private JPanel songInfoPanel;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public mainWindowGUI() {
        // Set the window
        super("JavaPlayer - Playlist");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,500);
        setMinimumSize(new Dimension(1000, 500));
        setContentPane(mainPanel);
        mainPanel.setMinimumSize(new Dimension(1000, 500));

        // Set the menu bar
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Files");
        menuBar.add(fileMenu);
        JMenuItem openSongMenuItem = new JMenuItem("Open file");
        openSongMenuItem.addActionListener(this);
        fileMenu.add(openSongMenuItem);

        JMenuItem exportPlaylistMenuItem = new JMenuItem("Export playlist");
        exportPlaylistMenuItem.addActionListener(this);
        fileMenu.add(exportPlaylistMenuItem);

        JMenu editMenu = new JMenu ("Edit");
        menuBar.add(editMenu);
        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        editMenu.add(settingsMenuItem);
        settingsMenuItem.addActionListener(this);

        homeButton.addActionListener(this);
        searchButton.addActionListener(this);
        favoritesButton.addActionListener(this);
        pausePlayButton.addActionListener(this);
        previousButton.addActionListener(this);
        nextButton.addActionListener(this);
        randomButton.addActionListener(this);
        loopButton.addActionListener(this);
        volumeButton.addActionListener(this);
        songIconButton.addActionListener(this);

        randomButton.setBorderPainted(false);
        previousButton.setBorderPainted(false);
        nextButton.setBorderPainted(false);
        randomButton.setBorderPainted(false);
        loopButton.setBorderPainted(false);
        volumeButton.setBorderPainted(false);

        timeSlider.setUI(new javax.swing.plaf.basic.BasicSliderUI(timeSlider) {
            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(229, 158, 221)); // Couleur de la piste
                g2.fillRect(trackRect.x, trackRect.y + trackRect.height / 2 - 1, trackRect.width, 3);
            }

            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(160, 43, 147)); // Couleur du curseur
                g2.fillOval(thumbRect.x, thumbRect.y + thumbRect.height / 2 - 4, 10, 10); // Curseur circulaire
            }
        });

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new HomePanelGUI().mainPanel, "Home");
        contentPanel.add(new PlaylistPanelGUI().mainPanel, "Playlist");

        contentScrollPane.setViewportView(contentPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) {
            cardLayout.show(contentPanel, "Home");
        } else if (e.getSource() == searchButton) {
            JOptionPane.showMessageDialog(this, "Recherche non implémentée !");
        } else if (e.getSource() == favoritesButton) {
            cardLayout.show(contentPanel, "Playlist");
        } else {
            JOptionPane.showMessageDialog(this, "Bouton non implémenté !");
        }
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        mainWindowGUI window = new mainWindowGUI();
        window.setVisible(true);
    }
}
