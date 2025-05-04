package com.javaPlayer.project.model.player;

import com.javaPlayer.project.model.entity.SongMetadata;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.datatype.Artwork;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MusicPlayer {
    private BasicPlayer player = new BasicPlayer();
    private boolean isPaused = false;
    private File currentFile;

    public MusicPlayer() {
        // Nothing to do
    }

//    private void openFile(ActionEvent e) {
//        JFileChooser chooser = new JFileChooser();
//        int result = chooser.showOpenDialog(null);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            currentFile = chooser.getSelectedFile();
//            getSongMetadata(currentFile);
//            playAudio();
//        }
//    }

    private void setCurrentFile(File file) {
        currentFile = file;
    }

    private void stopAudio() {
        try {
            player.stop();
            isPaused = false;
        } catch (BasicPlayerException ex) {
            ex.printStackTrace();
        }
    }

    private void playAudio() {
        try {
            if (isPaused) {
                player.resume();
                isPaused = false;
            } else {
                player.open(currentFile);
                player.play();
            }
        } catch (BasicPlayerException ex) {
            ex.printStackTrace();
        }
    }

    private void pauseAudio() {
        try {
            player.pause();
            isPaused = true;
        } catch (BasicPlayerException ex) {
            ex.printStackTrace();
        }
    }

    private void setVolume(double value) {
        try {
            player.setGain(value); // Between 0.0 and 1.0
        } catch (BasicPlayerException ex) {
            ex.printStackTrace();
        }
    }

//    private SongMetadata getSongMetadata(File file) {
//        try {
//            AudioFile audioFile = AudioFileIO.read(file);
//            Tag tag = audioFile.getTag();
//
//            Artwork artwork = tag.getFirstArtwork();
//
//            return new SongMetadata(tag.getFirst(org.jaudiotagger.tag.FieldKey.TITLE),
//                    tag.getFirst(org.jaudiotagger.tag.FieldKey.ARTIST),
//                    tag.getFirst(org.jaudiotagger.tag.FieldKey.ALBUM) ,
//                    tag.getFirst(org.jaudiotagger.tag.FieldKey.GENRE),
//                    artwork
//            );
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
}
