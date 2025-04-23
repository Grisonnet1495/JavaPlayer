package com.javaPlayer.project.view.GUI;

import javax.swing.*;
import java.awt.*;

public class JDialogSettings extends JDialog {
    public JPanel mainPanel;
    private JScrollPane mainContentScrollPane;
    private JPanel accountSettingsInPanel;
    private JTextField userPseudoTextField;
    private JPasswordField passwordTextField;
    private JPanel mainContentPanel;
    private JLabel accountSettingsLabel;
    private JPanel dangerZoneInPanel;
    private JLabel deleteAllDataLabel;
    private JButton deleteAllDataButton;
    private JCheckBox acceptDeleteAllDataCheckBox;
    private JLabel dangerZoneLabel;
    private JPanel deleteAllDataPanel;
    private JLabel userPseudoLabel;
    private JLabel changePasswordLabel;
    private JPanel accountSettingsOutPanel;
    private JPanel dangerZoneOutPanel;
    private JButton saveChangesButton;
    private JButton discardChangesButton;
    private JPanel saveSettingsPanel;
    private JLabel acceptDeleteAllDataLabel;

    private String newUserPseudo = null;
    private String newUserPassword = null;
    private boolean isDeletingAllData = false;
    private boolean isSaving = false;

    public JDialogSettings(JFrame parent, boolean modal, String actualUserPseudo, String actualUserPassword) {
        super(parent, "Settings", modal);
        this.setContentPane(mainPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Set the properties of the dialog box
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) ((float) screenSize.width / 2);
        int height = (int) ((float) screenSize.height / 2);
        this.setSize(new Dimension(width, height));
        this.setResizable(false);

        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

        // Add action listeners
        deleteAllDataButton.addActionListener(e -> {
            if (acceptDeleteAllDataCheckBox.isSelected()) {
                isDeletingAllData = true;
                deleteAllDataButton.setText("Please save to apply");
                acceptDeleteAllDataCheckBox.setEnabled(false);
                acceptDeleteAllDataLabel.setEnabled(false);
            }
        });

        saveChangesButton.addActionListener(e -> {
            newUserPseudo = userPseudoTextField.getText();
            newUserPassword = new String(passwordTextField.getPassword());
            isSaving = true;
            this.dispose();
        });

        discardChangesButton.addActionListener(e -> {
            this.dispose();
        });

        // Set the text fields
        userPseudoTextField.setText(actualUserPseudo);
        passwordTextField.setText(actualUserPassword);
    }

    public String getUserPseudo() {
        return newUserPseudo;
    }

    public String getUserPassword() {
        return newUserPassword;
    }

    public boolean isDeletingAllData() {
        return isDeletingAllData;
    }

    public boolean isSaving() {
        return isSaving;
    }
}
