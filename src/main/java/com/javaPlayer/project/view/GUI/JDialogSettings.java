package com.javaPlayer.project.view.GUI;

import javax.swing.*;

public class JDialogSettings extends JDialog {
    public JPanel mainPanel;
    private JScrollPane mainContentScrollPane;
    private JPanel accountSettingsInPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPanel mainContentPanel;
    private JLabel accountSettingsLabel;
    private JPanel dangerZoneInPanel;
    private JLabel deleteAllDataLabel;
    private JButton deleteAllDataButton;
    private JCheckBox acceptDeleteAllDataCheckBox;
    private JLabel dangerZoneLabel;
    private JPanel deleteAllDataPanel;
    private JLabel ImportOrExportLabel;
    private JLabel ImportFromFileLabel;
    private JTextField importFromFileTextField;
    private JTextField exportToFolderTextField;
    private JPanel importOrExportInPanel;
    private JLabel userPseudoLabel;
    private JLabel changePasswordLabel;
    private JLabel exportToFolderLabel;
    private JPanel accountSettingsOutPanel;
    private JPanel ImportOrExportOutPanel;
    private JPanel dangerZoneOutPanel;
    private JButton saveChangesButton;
    private JButton discardChangesButton;
    private JPanel saveSettingsPanel;

    public JDialogSettings(JFrame parent, boolean modal) {
        super(parent, "Settings", modal);
        this.setContentPane(mainPanel);
//        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
