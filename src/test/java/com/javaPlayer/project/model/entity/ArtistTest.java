package com.javaPlayer.project.model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArtistTest {

    @Test
    void getId() {
        Artist artist = new Artist(1, "Daft punk");
        assertEquals(1, artist.getId());//verifier que les deux valeurs sont egales
    }

    @Test
    void setId() {
        Artist artist = new Artist(1, "Daft punk");
        artist.setId(2);
        assertEquals(2, artist.getId());
    }

    @Test
    void getPseudo() {
        Artist artist = new Artist(1, "Daft punk");
        assertEquals("Daft punk", artist.getPseudo());
    }

    @Test
    void setPseudo() {
        Artist artist = new Artist(1, "Daft punk");
        artist.setPseudo("Vangelis");
        assertEquals("Vangelis", artist.getPseudo());
    }

    @Test
    void testToString() {
        Artist artist = new Artist(1, "Daft punk");
        assertEquals("Artist : Daft punk", artist.toString());
    }

    @Test
    void testEqualsForSameArtist() {
        Artist artist1 = new Artist(1, "Daft punk");
        Artist artist2 = new Artist(2, "Daft punk");
        assertEquals(artist1, artist2);
    }

    @Test
    void testEqualsForSameArtistWithDifferentCase() {
        Artist artist1 = new Artist(1, "Daft punk");
        Artist artist2 = new Artist(1, "daft punk");
        assertEquals(artist1, artist2);
    }

    @Test
    void testEqualsForDifferentArtist() {
        Artist artist1 = new Artist(1, "Daft punk");
        Artist artist2 = new Artist(2, "Vinsfeld");
        assertNotEquals(artist1, artist2);
    }
}
