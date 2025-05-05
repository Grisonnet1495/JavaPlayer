package com.javaPlayer.project.model.authentication;

public abstract class Authenticator {
    public boolean authenticate(String pseudo, String password) {
        return isLoginExists(pseudo) && getPassword(pseudo).equals(password);
    }

    public abstract void changeUserPseudo(String oldPseudo, String newPseudo);

    public abstract void changeUserPassword(String pseudo, String newPassword);

    public abstract boolean isLoginExists(String pseudo);

    public abstract String getPassword(String pseudo);

    public abstract void loadUsers();

    public abstract void saveUsers();

    public abstract void addUsers(String pseudo, String password);

    public abstract void removeUser(String pseudo);
}
