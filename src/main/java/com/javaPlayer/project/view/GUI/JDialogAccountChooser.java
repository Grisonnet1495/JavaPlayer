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
    private String pseudo;
    private String password;
    private boolean isConfirmed = false;
    private boolean isCreatingAccount = false;

    public JDialogAccountChooser() {
        // Set the window
//        super(parent, "JavaPlayer - Choose user", modal);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(350, 200);
        this.setResizable(false);

        loginButton.addActionListener(e -> {
            isConfirmed = true;
            setVisible(false);
        });

        createAccountButton.addActionListener(e -> {
            isCreatingAccount = true;
            isConfirmed = true;
            setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            setVisible(false);
        });
    }

    public String getPseudo() {
        pseudo = pseudoTextField.getText();
        return pseudoTextField.getText();
    }

    public String getPassword() {
        return new String(passwordTextField.getPassword());
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public boolean isCreatingAccount() { return isCreatingAccount; }
}
