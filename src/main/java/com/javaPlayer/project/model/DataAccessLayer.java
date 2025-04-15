package com.javaPlayer.project.model;

import com.javaPlayer.project.model.entity.Artist;
import com.javaPlayer.project.model.entity.Playlist;
import com.javaPlayer.project.model.entity.Song;
import com.javaPlayer.project.model.entity.User;

import java.util.ArrayList;

public interface DataAccessLayer {
    // CRUD functions
    int addPlaylist(Playlist article);

    boolean updatePlaylistTitle(String t);

    boolean deletePlaylist(int id);

    boolean deletePlaylist(Playlist p);

    Playlist getPlaylistById(int id);

    ArrayList<Playlist> getPlaylistList();
}
