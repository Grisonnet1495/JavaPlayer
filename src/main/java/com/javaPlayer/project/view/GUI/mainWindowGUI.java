package com.javaPlayer.project.view.GUI;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainWindowGUI extends JFrame implements ActionListener {
    // Main panel
    private JPanel mainPanel;

    // Menu bar
    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenuItem openSongMenuItem;
    private final JMenu editMenu;
    private final JMenuItem settingsMenuItem;
    private final JMenuItem exportPlaylistMenuItem;


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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,500);
        this.setMinimumSize(new Dimension(1000, 500));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setContentPane(mainPanel);

        mainPanel.setMinimumSize(new Dimension(1000, 500));

        // Set the menu bar
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        // Set the different menus
        fileMenu = new JMenu("Files");
        editMenu = new JMenu ("Edit");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        // Set the menu items
        openSongMenuItem = new JMenuItem("Open file");
        settingsMenuItem = new JMenuItem("Settings");
        exportPlaylistMenuItem = new JMenuItem("Export playlist");
        fileMenu.add(openSongMenuItem);
        fileMenu.add(exportPlaylistMenuItem);
        editMenu.add(settingsMenuItem);

        // Add all action listener
        openSongMenuItem.addActionListener(this);
        exportPlaylistMenuItem.addActionListener(this);
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

        // Delete the border of some buttons
        randomButton.setBorderPainted(false);
        previousButton.setBorderPainted(false);
        nextButton.setBorderPainted(false);
        randomButton.setBorderPainted(false);
        loopButton.setBorderPainted(false);
        volumeButton.setBorderPainted(false);

        // Set the UI of timeSlider
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

        // Create the layout for the main content
        cardLayout = new CardLayout(); // Note : On peut raccourcir ?
        contentPanel = new JPanel(cardLayout);

        // Add the main content
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
        } else if (e.getSource() == settingsMenuItem) {
            // Create the settings dialog box
            JDialog settingsDialog = new JDialog(this, true);
//            dialog.setLocation(100,100);
            settingsDialog.setTitle("Settings");

            // Set the properties of the dialog box
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int)((float)screenSize.width / 1.5);
            int height = (int)((float)screenSize.height / 1.5);
            settingsDialog.setSize(new Dimension(width, height));
            settingsDialog.setResizable(false);

            int x = (screenSize.width - settingsDialog.getWidth()) / 2;
            int y = (screenSize.height - settingsDialog.getHeight()) / 2;
            settingsDialog.setLocation(x, y);

            settingsDialog.setContentPane(new SettingsPanelGUI().mainPanel);
            settingsDialog.setVisible(true);
            settingsDialog.dispose();
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
