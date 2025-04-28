package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.utils.DefaultFilePath;

import java.io.*;
import java.util.Properties;

public class DAOConfig {
    public String configFilename;
    Properties config = new Properties();;

    public DAOConfig(String configFilename) {
        this.configFilename = configFilename;
        setupConfig();
        loadConfig();
    }

    public void setupConfig() {
        try {
            File configFile = new File(configFilename);
            if (!configFile.exists()) {
                configFile.createNewFile();
            }

            loadConfig();

            if (!isConfigExists("userFile")) {
                addConfig("userFile", DefaultFilePath.USERS);
            }

            if (!isConfigExists("playlistsFile")) {
                addConfig("playlistsFile", DefaultFilePath.PLAYLISTS);
            }

            saveConfig();

        } catch (IOException | SecurityException e) {
            System.out.println("Error creating config file");
        }
    }

    public void loadConfig() {
        try (FileInputStream fis = new FileInputStream(configFilename)) {
            config.load(fis);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    public void saveConfig() {
        try (FileOutputStream fos = new FileOutputStream(configFilename)) {
            config.store(fos, "Config");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    public void addConfig(String configName, String configValue) {
        if (!config.containsKey(configName)) {
            config.put(configName, configValue);
            saveConfig();
            System.out.println("Config added successfully.");
        } else {
            System.out.println("Config already exists.");
        }
    }

    public void removeConfig(String configName) {
        if (config.containsKey(configName)) {
            config.remove(configName);
            saveConfig(); // Update file
            System.out.println("Config removed successfully.");
        } else {
            System.out.println("Cannot find config.");
        }
    }

    public boolean isConfigExists(String pseudo) {
        return config.containsKey(pseudo);
    }

    public String getConfig(String configName) {
        return (String)config.getOrDefault(configName, null);
    }
}
