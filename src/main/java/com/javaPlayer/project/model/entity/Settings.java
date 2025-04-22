package com.javaPlayer.project.model.entity;

public class Settings {
    String userPseudo;
    String userPassword;
    boolean isDeletingAllData;

    public Settings(String userPseudo, String userPassword, boolean isDeletingAllData) {
        this.userPseudo = userPseudo;
        this.userPassword = userPassword;
        this.isDeletingAllData = isDeletingAllData;
    }

    public String getUserPseudo() {
        return userPseudo;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public boolean isDeletingAllData() {
        return isDeletingAllData;
    }
}
