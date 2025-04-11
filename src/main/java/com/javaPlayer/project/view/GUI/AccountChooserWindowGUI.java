package com.javaPlayer.project.view.GUI;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;

public class AccountChooserWindowGUI extends JFrame {
    private JPanel mainPanel;
    private JButton guestUserButton;
    private JButton createAccountButton;
    private JPanel contentPanel;
    private JLabel guestUserLabel;
    private JLabel createAccountLabel;

    public AccountChooserWindowGUI() {
        // Set the window
        super("JavaPlayer - Choose user");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,500);
        this.setResizable(false);

        // Set the size of the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        AccountChooserWindowGUI window = new AccountChooserWindowGUI();
        window.setVisible(true);
    }
}
