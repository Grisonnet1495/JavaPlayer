package com.javaPlayer.project.model.player;

import com.javaPlayer.project.controller.Controller;
import com.javaPlayer.project.controller.ControllerActions;
import com.javaPlayer.project.model.entity.SongMetadata;
import com.javaPlayer.project.model.exception.MusicPlayerException;
import com.javaPlayer.project.utils.Constants;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.images.ArtworkFactory;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.base.State;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;

import java.awt.event.ActionEvent;
import java.io.File;
import java.time.Duration;

public class MusicPlayer implements IMusicPlayer {

    private final MediaPlayerFactory factory;
    private final MediaPlayer mediaPlayer;
    private Controller controller;
    private boolean released = false;

    public MusicPlayer() {
        this.factory = new MediaPlayerFactory();
        this.mediaPlayer = factory.mediaPlayers().newMediaPlayer();
    }

    @Override
    public void setController(Controller c) {
        controller = c;

        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                ActionEvent event = new ActionEvent(mediaPlayer, ActionEvent.ACTION_PERFORMED, ControllerActions.CHOOSE_NEW_SONG);
                javax.swing.SwingUtilities.invokeLater(() -> controller.actionPerformed(event));
            }
        });
    }

    // Load a music file from the start and play it
    @Override
    public void loadAndPlay(String filePath) {
        State state = mediaPlayer.status().state();
        if (state == State.PLAYING || state == State.PAUSED) {
            mediaPlayer.controls().stop();

            // Make a pause
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
        }

        mediaPlayer.media().play(filePath);
    }

    // Play or resume the music
    @Override
    public void resume() {
        State state = mediaPlayer.status().state();
        if (state == State.PAUSED || state == State.STOPPED) {
            mediaPlayer.controls().play();
        }
    }

    // Pause the music
    @Override
    public void pause() {
        if (mediaPlayer.status().state() == State.PLAYING) {
            mediaPlayer.controls().pause();
        }
    }

    // Stop the music
    @Override
    public void stop() {
        State state = mediaPlayer.status().state();
        if (state == State.PLAYING || state == State.PAUSED) {
            mediaPlayer.controls().stop();
        }
    }

    // Free all VLC resources
    @Override
    public void release() {
        if (!released) {
            mediaPlayer.release();
            factory.release();
            released = true;
        }
    }

    // Get current position in the music in milliseconds
    @Override
    public long getCurrentPosition() {
        return mediaPlayer.status().time(); // Note : Return 0 if there is no media loaded
    }

    // Get the total duration of the music in milliseconds
    @Override
    public long getTotalDuration() {
        // If the media is loaded
        if (mediaPlayer.media().info().mrl() != null) {
            return mediaPlayer.status().length();
        }

        return 0;
    }

    // Go to a specific position in the music
    @Override
    public void seek(long position) {
        State state = mediaPlayer.status().state();
        if (state == State.PLAYING || state == State.PAUSED || state == State.STOPPED) {
            mediaPlayer.controls().setTime(position);
        }
    }

    // Return if a music is currently playing
    @Override
    public boolean isPlaying() {
        return mediaPlayer.status().isPlaying();
    }

    // Set the volume
    @Override
    public void setVolume(int volume) {
        mediaPlayer.audio().setVolume(volume);
    }

    // Get the song metadata
    @Override
    public SongMetadata getSongMetadata(String filePath) {
        try {
            AudioFile audioFile = AudioFileIO.read(new File(filePath));
            Tag tag = audioFile.getTag();
            int durationInSeconds = audioFile.getAudioHeader().getTrackLength();
            Duration duration = Duration.ofSeconds(durationInSeconds);

            return new SongMetadata(
                    tag != null && tag.getFirst(FieldKey.TITLE) != null ? tag.getFirst(FieldKey.TITLE) : "Unknown title",
                    tag != null && tag.getFirst(FieldKey.ARTIST) != null ? tag.getFirst(FieldKey.ARTIST) : "Unknown author",
                    tag != null && tag.getFirst(FieldKey.ALBUM) != null ? tag.getFirst(FieldKey.ALBUM) : "Unknown album",
                    tag != null && tag.getFirst(FieldKey.GENRE) != null ? tag.getFirst(FieldKey.GENRE) : "Unknown genre",
                    duration,
                    imageToBytes(filePath)
            );
        } catch (Exception e) {
            throw new MusicPlayerException("Error while reading file metadata: " + e.getMessage());
        }
    }

    // Get the song icon
    @Override
    public byte[] getSongIcon(String filePath) {
        return imageToBytes(filePath);
    }

    // Extract an image from a music file
    private byte[] imageToBytes(String filePath) {
        try {
            AudioFile audioFile = AudioFileIO.read(new File(filePath));
            Tag tag = audioFile.getTag();
            if (tag != null && tag.getFirstArtwork() != null) {
                return tag.getFirstArtwork().getBinaryData();
            }
            File defaultImg = new File(Constants.DEFAULT_PLAYLIST_ICON);
            if (defaultImg.exists()) {
                return ArtworkFactory.createArtworkFromFile(defaultImg)
                        .getBinaryData();
            }
        } catch (Exception e) {
            throw new MusicPlayerException("Cannot read song icon from " + filePath, e);
        }

        return null;
    }
}
