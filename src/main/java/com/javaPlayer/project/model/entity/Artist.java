package com.javaPlayer.project.model.entity;

public class Artist {
    private int id;
    private String pseudo;

    public Artist(int id, String pseudo) {
        this.id = id;
        this.pseudo = pseudo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
