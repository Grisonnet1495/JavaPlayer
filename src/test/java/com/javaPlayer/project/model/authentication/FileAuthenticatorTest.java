package com.javaPlayer.project.model.authentication;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class FileAuthenticatorTest {
    private FileAuthenticator fileAuthenticator;
    private final String TEST_USER_PASSWORDS_FILE = "testUserPasswords.properties";

    @BeforeEach
    void setUp() {
        fileAuthenticator = new FileAuthenticator(TEST_USER_PASSWORDS_FILE);
        fileAuthenticator.addUsers("Tom", "1234");
        fileAuthenticator.addUsers("Sacha", "abcd");
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_USER_PASSWORDS_FILE);

        if (file.exists()) {
            file.delete();
        }

        fileAuthenticator = null;
    }

    @Test
    void loadUsers() {
        Properties props = new Properties();
        props.setProperty("testUser1", "testPassword1");
        props.setProperty("testUser2", "testPassword2");

        File file = new File(TEST_USER_PASSWORDS_FILE);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            props.store(fos, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        fileAuthenticator.loadUsers();

        assertEquals("testPassword1", fileAuthenticator.getPassword("testUser1"));
        assertEquals("testPassword2", fileAuthenticator.getPassword("testUser2"));
    }

    @Test
    void saveUsers() {
        fileAuthenticator.saveUsers();

        Properties props = new Properties();

        File file = new File(TEST_USER_PASSWORDS_FILE);
        try (FileInputStream fos = new FileInputStream(file)) {
            props.load(fos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals("1234", fileAuthenticator.getPassword("Tom"));
        assertEquals("abcd", fileAuthenticator.getPassword("Sacha"));
    }

    @Test
    void addUsers() {
        fileAuthenticator.addUsers("testUser", "testPassword");
        assertEquals("testPassword", fileAuthenticator.getPassword("testUser"));
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
        assertFalse(fileAuthenticator.isLoginExists("testUser"));
    }

    @Test
    void getPassword() {
        assertEquals("1234", fileAuthenticator.getPassword("Tom"));
        assertEquals("abcd", fileAuthenticator.getPassword("Sacha"));
    }
}
