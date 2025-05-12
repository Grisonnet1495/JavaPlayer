package com.javaPlayer.project.model.player;

import com.javaPlayer.project.model.entity.SongMetadata;
import com.javaPlayer.project.model.exception.MusicPlayerException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.images.ArtworkFactory;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.media.MediaParsedStatus;
import uk.co.caprica.vlcj.media.Meta;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.base.State;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class MusicPlayer implements IMusicPlayer {

    private final MediaPlayerFactory factory;
    private final MediaPlayer mediaPlayer;
    private boolean released = false;

    public MusicPlayer() {
        this.factory = new MediaPlayerFactory();
        this.mediaPlayer = factory.mediaPlayers().newMediaPlayer();
    }

    // Load a music file from the start and play it
    public void loadAndPlay(String filePath) {
        State state = mediaPlayer.status().state();
        if (state == State.PLAYING) {
            mediaPlayer.controls().stop();
        }
        mediaPlayer.media().play(filePath);
    }

    // Play or resume the music
    public void play() {
        State state = mediaPlayer.status().state();
        if (state == State.PAUSED || state == State.STOPPED) {
            mediaPlayer.controls().play();
        }
    }

    // Pause the music
    public void pause() {
        if (mediaPlayer.status().state() == State.PLAYING) {
            mediaPlayer.controls().pause();
        }
    }

    // Stop the music
    public void stop() {
        State state = mediaPlayer.status().state();
        if (state == State.PLAYING || state == State.PAUSED) {
            mediaPlayer.controls().stop();
        }
    }

    // Free all VLC resources
    public void release() {
        if (!released) {
            mediaPlayer.release();
            factory.release();
            released = true;
        }
    }

    // Return if a music is currently playing
    public boolean isPlaying() {
        return mediaPlayer.status().isPlaying();
    }

    // Set the volume
    public void setVolume(int volume) {
        mediaPlayer.audio().setVolume(volume);
    }

    // Get the song metadata
    public SongMetadata getSongMetadata(String filePath) {
        try {
            AudioFile audioFile = AudioFileIO.read(new File(filePath));
            Tag tag = audioFile.getTag();
            int durationInSeconds = audioFile.getAudioHeader().getTrackLength();
            Duration duration = Duration.ofSeconds(durationInSeconds);

            SongMetadata metadata = new SongMetadata(
                    tag != null ? tag.getFirst(FieldKey.TITLE) : null,
                    tag != null ? tag.getFirst(FieldKey.ARTIST) : null,
                    tag != null ? tag.getFirst(FieldKey.ALBUM) : null,
                    tag != null ? tag.getFirst(FieldKey.GENRE) : null,
                    duration,
                    imageToBytes(filePath)
            );

            System.out.println(metadata); // Affiche l'objet en console

            return metadata;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MusicPlayerException("Erreur lors de la lecture des métadonnées : " + e.getMessage(), e);
        }
    }


    // Get the song icon
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
            File defaultImg = new File("/icons/default_song_icon_black.png");
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
