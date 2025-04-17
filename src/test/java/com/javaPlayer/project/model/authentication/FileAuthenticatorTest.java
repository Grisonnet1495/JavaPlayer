package com.javaPlayer.project.model.authentication;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileAuthenticatorTest {
    private static FileAuthenticator fileAuthenticator;

    @BeforeAll
    static void setUp() {
        fileAuthenticator = new FileAuthenticator();
        // Note : Not good ?
        fileAuthenticator.addUsers("Tom", "1234");
        fileAuthenticator.addUsers("Sacha", "abcd");
    }

    @Test
    void uploadUsers() {
        // Note : Not good ?
        fileAuthenticator.saveUsers();
        fileAuthenticator.uploadUsers();
        assert(fileAuthenticator.isLoginExists("Tom"));
        assertEquals("1234", fileAuthenticator.getPassword("Tom"));
        assert(fileAuthenticator.isLoginExists("Sacha"));
        assertEquals("abcd", fileAuthenticator.getPassword("Sacha"));
    }

    @Test
    void saveUsers() {
        // Note : Not good ?
        fileAuthenticator.saveUsers();
        fileAuthenticator.uploadUsers();
        assert(fileAuthenticator.isLoginExists("Tom"));
        assertEquals("1234", fileAuthenticator.getPassword("Tom"));
        assert(fileAuthenticator.isLoginExists("Sacha"));
        assertEquals("abcd", fileAuthenticator.getPassword("Sacha"));
    }

    @Test
    void addUsers() {
        // Note : Not good ?
        fileAuthenticator.addUsers("Noa", "motDePasse");
        assert(fileAuthenticator.isLoginExists("Noa"));
        assertEquals("motDePasse", fileAuthenticator.getPassword("Noa"));
    }

    @Test
    void removeUser() {
        // Note : Not good ?
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