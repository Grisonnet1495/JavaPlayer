package com.javaPlayer.project.model.dao;

import java.io.*;
import java.util.Properties;

public class DAOConfig {
    public String configFileName;
    Properties config = new Properties();;

    public DAOConfig(String configFileName) {
        this.configFileName = configFileName;
        loadConfig();
    }

    public void loadConfig() {
        try (FileInputStream fis = new FileInputStream(configFileName)) {
            config.load(fis);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    public void saveConfig() {
        try (FileOutputStream fos = new FileOutputStream(configFileName)) {
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
