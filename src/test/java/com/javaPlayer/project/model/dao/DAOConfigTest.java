package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.utils.Constants;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
class DAOConfigTest {
    private static final String TEST_CONFIG_FILENAME = "testConfig.properties";
    private DAOConfig daoConfig;

    @BeforeEach
    void setUp() {
        daoConfig = new DAOConfig(TEST_CONFIG_FILENAME);
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_CONFIG_FILENAME);
        if (file.exists()) {
            file.delete();
        }

        daoConfig = null;
    }

    @Test
    void loadConfig() {
        Properties props = new Properties();
        props.setProperty("testKey1", "testValue1");
        props.setProperty("testKey2", "testValue2");

        File file = new File(TEST_CONFIG_FILENAME);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            props.store(fos, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        daoConfig.loadConfig();

        assertEquals("testValue1", daoConfig.getConfig("testKey1"));
        assertEquals("testValue2", daoConfig.getConfig("testKey2"));
    }

    @Test
    void saveConfig() {
        daoConfig.saveConfig();

        Properties props = new Properties();

        File file = new File(TEST_CONFIG_FILENAME);
        try (FileInputStream fos = new FileInputStream(file)) {
            props.load(fos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(daoConfig.getConfig(Constants.USERS_CONFIG_KEY), props.getProperty(Constants.USERS_CONFIG_KEY));
        assertEquals(daoConfig.getConfig(Constants.USER_PASSWORDS_CONFIG_KEY), props.getProperty(Constants.USER_PASSWORDS_CONFIG_KEY));
        assertEquals(daoConfig.getConfig(Constants.USER_PLAYLISTS_CONFIG_KEY), props.getProperty(Constants.USER_PLAYLISTS_CONFIG_KEY));
    }

    @Test
    void addConfig() {
        daoConfig.addConfig("testConfig", "testValue");
        assertEquals("testValue", daoConfig.getConfig("testConfig"));
    }

    @Test
    void removeConfig() {
        daoConfig.removeConfig(Constants.USERS_CONFIG_KEY);
        assertNull(daoConfig.getConfig(Constants.USERS_CONFIG_KEY));
    }

    @Test
    void getConfig() {
        assertEquals(Constants.USERS_FILENAME, daoConfig.getConfig(Constants.USERS_CONFIG_KEY));
    }

    @Test
    void isConfigPresent() {
        assertTrue(daoConfig.isConfigPresent(Constants.USERS_CONFIG_KEY));
    }
}
