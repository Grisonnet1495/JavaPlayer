package com.javaPlayer.project.model.entity;

public class Credentials {
    private boolean isCancellingRequest;
    private boolean isCreatingAccount;
    private String username;
    private String password;

    public Credentials(boolean isCancellingRequest, boolean isCreatingAccount, String username, String password) {
        this.isCancellingRequest = isCancellingRequest;
        this.isCreatingAccount = isCreatingAccount;
        this.username = username;
        this.password = password;
    }

    public boolean isCancellingRequest() {
        return isCancellingRequest;
    }

    public boolean isCreatingAccount() {
        return isCreatingAccount;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
