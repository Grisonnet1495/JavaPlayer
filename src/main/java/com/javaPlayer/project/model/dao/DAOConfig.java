package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.exception.ConfigException;
import com.javaPlayer.project.utils.Constants;

import java.io.*;
import java.util.Properties;

public class DAOConfig implements IDAOConfig {
    public String configFilename;
    Properties config;

    public DAOConfig(String configFilename) {
        config = new Properties();
        setupConfig(configFilename);
    }

    private void setupConfig(String filename) {
        try {
            // Create the config file if it doesn't exist
            configFilename = filename;

            File configFile = new File(configFilename);
            if (!configFile.exists()) {
                if (!configFile.createNewFile()) {
                    throw new ConfigException("Cannot create config file");
                }
            }

            loadConfig();

            // Add the users config filename if it doesn't exist
            if (!isConfigPresent(Constants.USERS_CONFIG_KEY)) {
                addConfig(Constants.USERS_CONFIG_KEY, Constants.USERS_FILENAME);
            }

            // Add the users password filename if it doesn't exist
            if (!isConfigPresent(Constants.USER_PASSWORDS_CONFIG_KEY)) {
                addConfig(Constants.USER_PASSWORDS_CONFIG_KEY, Constants.USER_PASSWORDS_FILENAME);
            }

            // Add the user playlists config filename if it doesn't exist
            if (!isConfigPresent(Constants.USER_PLAYLISTS_CONFIG_KEY)) {
                addConfig(Constants.USER_PLAYLISTS_CONFIG_KEY, Constants.USER_PLAYLISTS_FILENAME);
            }

            saveConfig();
        } catch (IOException | SecurityException e) {
            throw new ConfigException("Cannot setup config file '" + configFilename + "' : " + e.getMessage());
        }
    }

    @Override
    public void loadConfig() {
        try (FileInputStream fis = new FileInputStream(configFilename)) {
            config.load(fis);
        } catch (FileNotFoundException e) {
            throw new ConfigException("Config file not found : " + e.getMessage());
        } catch (IOException e) {
            throw new ConfigException("IO Exception : " + e.getMessage());
        }
    }

    @Override
    public void saveConfig() {
        try (FileOutputStream fos = new FileOutputStream(configFilename)) {
            config.store(fos, "Config");
        } catch (FileNotFoundException e) {
            throw new ConfigException("Config file not found : " + e.getMessage());
        } catch (IOException e) {
            throw new ConfigException("IO Exception : " + e.getMessage());
        }
    }

    @Override
    public void addConfig(String configName, String configValue) {
        if (!config.containsKey(configName)) {
            config.put(configName, configValue);
            saveConfig();
        } else {
            throw new ConfigException("Config already exists");
        }
    }

    @Override
    public void removeConfig(String configName) {
        if (config.containsKey(configName)) {
            config.remove(configName);
            saveConfig();
        } else {
            throw new ConfigException("Config does not exist");
        }
    }

    @Override
    public String getConfig(String configName) {
        return (String)config.get(configName);
    }

    @Override
    public boolean isConfigPresent(String configName) {
        return config.containsKey(configName);
    }
}
