package com.javaPlayer.project.model.dao;

import com.javaPlayer.project.model.exception.ConfigException;
import com.javaPlayer.project.utils.Constants;

import java.io.*;

public interface IDAOConfig {
    // Setup methods
    public void setupConfig(String filename);

    // Backup methods
    public void loadConfig();
    public void saveConfig();

    // CRUD methods
    public void addConfig(String configName, String configValue);
    public void removeConfig(String configName);
    public String getConfig(String configName);

    // Check methods
    public boolean isConfigPresent(String configName);
}
