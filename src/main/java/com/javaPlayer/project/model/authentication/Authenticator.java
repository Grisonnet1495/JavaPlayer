package com.javaPlayer.project.model.authentication;

public abstract class Authenticator {
    public boolean authenticate(String pseudo, String password) {
        return isLoginExists(pseudo) && getPassword(pseudo).equals(password);
    }

    protected abstract boolean isLoginExists(String pseudo);

    protected abstract String getPassword(String pseudo);

    public abstract void uploadUsers();

    public abstract void saveUsers();

    public abstract void addUsers(String pseudo, String password);

    public abstract void removeUser(String pseudo);
}
