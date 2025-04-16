package com.javaPlayer.project.model.entity;

public abstract class Authenticator {
    public abstract boolean authenticate(String pseudo, String password);
    protected abstract boolean isLoginExists(String pseudo);
    protected abstract String getPassword(String pseudo);
}
