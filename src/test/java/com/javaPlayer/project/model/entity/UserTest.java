package com.javaPlayer.project.model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void getAndSetId() {
        User user = new User("testUser", "password");
        user.setId(10);
        assertEquals(10, user.getId());
    }

    @Test
    void testGetAndSetPseudo() {
        User user = new User("testUser", "password");
        user.setPseudo("newPseudo");
        assertEquals("newPseudo", user.getPseudo());
    }

    @Test
    void testGetAndSetPassword() {
        User user = new User("testUser", "password");
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    void testToString() {
        User user = new User("testUser", "password");
        user.setId(5);
        String expected = "User{id=5, pseudo='testUser'}";
        assertEquals(expected, user.toString());
    }

    @Test
    void testEquals() {
        User user1 = new User("user1", "pass1");
        user1.setId(1);

        User user2 = new User("user2", "pass2");
        user2.setId(1);

        User user3 = new User("user3", "pass3");
        user3.setId(2);

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
    }

    @Test
    void testHashCode() {
        User user1 = new User("user1", "pass1");
        user1.setId(1);

        User user2 = new User("user2", "pass2");
        user2.setId(1);

        User user3 = new User("user3", "pass3");
        user3.setId(2);

        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }
}
