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

    public String toString()
    {
        return "Artist : " + pseudo;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false; //garantir qu'on compare deux objets de meme type
        Artist artist = (Artist)obj;//cast l'objet a comparer en artiste
        return pseudo != null && pseudo.equalsIgnoreCase(artist.getPseudo());//
    }
}
