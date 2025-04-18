package com.javaPlayer.project.model.authentication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileAuthenticatorTest {
    private static FileAuthenticator fileAuthenticator;
    private static final String fileName = "filename.properties";

    @BeforeAll
    static void setUp() {
        // Suppression du fichier à la fin
        File file = new File(fileName);
        if (file.exists()) file.delete();

        fileAuthenticator = new FileAuthenticator(fileName);
        fileAuthenticator.addUsers("Tom", "1234");
        fileAuthenticator.addUsers("Sacha", "abcd");

    }

    @AfterEach
    void tearDown() {
        File file = new File(fileName);
        if (file.exists()) file.delete();
    }


    @Test
    void uploadUsers() {
        fileAuthenticator.saveUsers(fileName);
        fileAuthenticator.uploadUsers();
        assert(fileAuthenticator.isLoginExists("Tom"));
        assertEquals("1234", fileAuthenticator.getPassword("Tom"));
        assert(fileAuthenticator.isLoginExists("Sacha"));
        assertEquals("abcd", fileAuthenticator.getPassword("Sacha"));
    }

    @Test
    void saveUsers() {
        fileAuthenticator.saveUsers(fileName);
        fileAuthenticator.uploadUsers();
        assert(fileAuthenticator.isLoginExists("Tom"));
        assertEquals("1234", fileAuthenticator.getPassword("Tom"));
        assert(fileAuthenticator.isLoginExists("Sacha"));
        assertEquals("abcd", fileAuthenticator.getPassword("Sacha"));
    }

    @Test
    void addUsers() {
        fileAuthenticator.addUsers("Noa", "motDePasse");
        assert(fileAuthenticator.isLoginExists("Noa"));
        assertEquals("motDePasse", fileAuthenticator.getPassword("Noa"));
    }

    @Test
    void removeUser() {
        fileAuthenticator.removeUser("Tom");
        assert(!fileAuthenticator.isLoginExists("Tom"));
        assert(fileAuthenticator.isLoginExists("Sacha"));
    }

    @Test
    void authenticate() {
        assert(fileAuthenticator.authenticate("Tom", "1234"));
        assert(!fileAuthenticator.authenticate("Tom", "abcd"));
        assert(fileAuthenticator.authenticate("Sacha", "abcd"));
        assert(!fileAuthenticator.authenticate("Sacha", "1234"));
    }

    @Test
    void isLoginExists() {
        assert(fileAuthenticator.isLoginExists("Tom"));
        assert(!fileAuthenticator.isLoginExists("Noa"));
    }

    @Test
    void getPassword() {
        assertEquals("1234", fileAuthenticator.getPassword("Tom"));
        assertEquals("abcd", fileAuthenticator.getPassword("Sacha"));
    }
}