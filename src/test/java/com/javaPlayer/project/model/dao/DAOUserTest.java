package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class DAOUserTest {
    private final String TEST_USER_FILE = "testUser.dat";
    private DAOUser daoUser;

    @BeforeEach
    void setUp() {
        daoUser = new DAOUser(TEST_USER_FILE);
        daoUser.addUser(new User("Tom", "1234"));
        daoUser.addUser(new User("Sacha", "abcd"));
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_USER_FILE);
        if (file.exists()) {
            file.delete();
        }

        daoUser = null;
    }

    @Test
    void loadUAndSaveUsersToFile() {
        daoUser.saveUsersToFile();
        daoUser.loadUsersFromFile();

        assertEquals("1234", daoUser.getUserByPseudo("Tom").getPassword());
        assertEquals("abcd", daoUser.getUserByPseudo("Sacha").getPassword());
    }

    @Test
    void addUser() {
        daoUser.addUser(new User("testUser", "testPassword"));
        assertEquals("testPassword", daoUser.getUserByPseudo("testUser").getPassword());
    }

    @Test
    void removeUserById() {
        daoUser.removeUserById(1);
        assertNull(daoUser.getUserById(1));
    }

    @Test
    void getUserById() {
        assertEquals("Tom", daoUser.getUserById(1).getPseudo());
    }

    @Test
    void getUserByPseudo() {
        assertEquals("Tom", daoUser.getUserByPseudo("Tom").getPseudo());
    }

    @Test
    void updateUserById() {
        daoUser.updateUserById(2, "Sacha2", "testPassword2");
        assertEquals("testPassword2", daoUser.getUserByPseudo("Sacha2").getPassword());
    }
}
