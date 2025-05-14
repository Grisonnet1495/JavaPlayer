package com.javaPlayer.project.model.dao;

public interface IDAOConfig {
    // Setup methods
    void setupConfig(String filename);

    // Backup methods
    void loadConfig();
    void saveConfig();

    // CRUD methods
    void addConfig(String configName, String configValue);
    void removeConfig(String configName);
    String getConfig(String configName);

    // Check methods
    boolean isConfigPresent(String configName);
}
