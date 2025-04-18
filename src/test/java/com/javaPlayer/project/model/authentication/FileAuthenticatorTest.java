package com.javaPlayer.project.model.authentication;

import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileAuthenticatorTest {
    private static FileAuthenticator fileAuthenticator;
    private static String fileName = "testUser.properties";
    private static File file = null;

    @BeforeEach
    void setUp() {
        // Suppression du fichier à la fin
        file = new File(fileName);
        if (file.exists()) file.delete();

        fileAuthenticator = new FileAuthenticator(fileName);
        fileAuthenticator.addUsers("Tom", "1234");
        fileAuthenticator.addUsers("Sacha", "abcd");
        fileAuthenticator.saveUsers();
    }

    @AfterEach
    void deleteFile() {
        if (file.exists()) file.delete();
    }

    @Test
    void loadUsers() {
        fileAuthenticator.saveUsers();
        fileAuthenticator.loadUsers();
        assertTrue(fileAuthenticator.isLoginExists("Tom"));
        assertEquals("1234", fileAuthenticator.getPassword("Tom"));
        assertTrue(fileAuthenticator.isLoginExists("Sacha"));
        assertEquals("abcd", fileAuthenticator.getPassword("Sacha"));
    }

    @Test
    void saveUsers() {
        fileAuthenticator.saveUsers();
        fileAuthenticator.loadUsers();
        assertTrue(fileAuthenticator.isLoginExists("Tom"));
        assertEquals("1234", fileAuthenticator.getPassword("Tom"));
        assertTrue(fileAuthenticator.isLoginExists("Sacha"));
        assertEquals("abcd", fileAuthenticator.getPassword("Sacha"));
    }

    @Test
    void addUsers() {
        fileAuthenticator.addUsers("Noa", "motDePasse");
        assertTrue(fileAuthenticator.isLoginExists("Noa"));
        assertEquals("motDePasse", fileAuthenticator.getPassword("Noa"));
    }

    @Test
    void removeUser() {
        fileAuthenticator.removeUser("Tom");
        assertFalse(fileAuthenticator.isLoginExists("Tom"));
        assertTrue(fileAuthenticator.isLoginExists("Sacha"));
    }

    @Test
    void authenticate() {
        assertTrue(fileAuthenticator.authenticate("Tom", "1234"));
        assertFalse(fileAuthenticator.authenticate("Tom", "abcd"));
        assertTrue(fileAuthenticator.authenticate("Sacha", "abcd"));
        assertFalse(fileAuthenticator.authenticate("Sacha", "1234"));
    }

    @Test
    void isLoginExists() {
        assertTrue(fileAuthenticator.isLoginExists("Tom"));
        assertFalse(fileAuthenticator.isLoginExists("Noa"));
    }

    @Test
    void getPassword() {
        assertEquals("1234", fileAuthenticator.getPassword("Tom"));
        assertEquals("abcd", fileAuthenticator.getPassword("Sacha"));
    }
}
