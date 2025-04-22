package com.javaPlayer.project.view.GUI;

import javax.swing.*;

public class JDialogAccountChooser extends JDialog {
    private JPanel mainPanel;
    private JTextField pseudoTextField;
    private JPasswordField passwordTextField;
    private JButton cancelButton;
    private JButton loginButton;
    private JPanel entryPanel;
    private JPanel validationButtonsPanel;
    private JLabel passwordLabel;
    private JLabel pseudoLabel;
    private JButton createAccountButton;
    private boolean isCancelled = true;
    private boolean isCreatingAccount = false;

    public JDialogAccountChooser() {
        // Set the window
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Set the properties of the dialog box
        this.setSize(350, 200);
        this.setResizable(false);

        // Add action listeners
        loginButton.addActionListener(e -> {
            this.setVisible(false);
            isCancelled = false;
        });

        createAccountButton.addActionListener(e -> {
            isCreatingAccount = true;
            isCancelled = false;
            this.setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            this.setVisible(false);
        });
    }

    public String getPseudo() {
        return pseudoTextField.getText();
    }

    public String getPassword() {
        return new String(passwordTextField.getPassword());
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public boolean isCreatingAccount() { return isCreatingAccount; }
}
