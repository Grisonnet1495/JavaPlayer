package com.javaPlayer.project.model.player;

import com.javaPlayer.project.model.entity.SongMetadata;
import com.javaPlayer.project.model.exception.MusicPlayerException;
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
        // Initialize a thread to wait counter
        CountDownLatch latch = new CountDownLatch(1);
        final SongMetadata[] songMetadata = new SongMetadata[1];  // VLC can only parse file metadata in a final object.
                                                            // To bypass this, we can use a SongMetada table, as the content of the table can change even if the table is final.

        // Add actions for when the media is parsed
        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            public void mediaParsedChanged(MediaPlayer mp, MediaParsedStatus status) {
                if (status == MediaParsedStatus.DONE) {
                    songMetadata[0] = new SongMetadata(
                            mp.media().meta().get(Meta.TITLE),
                            mp.media().meta().get(Meta.ARTIST),
                            mp.media().meta().get(Meta.ALBUM),
                            mp.media().meta().get(Meta.GENRE),
                            imageToBytes(filePath)
                    );
                    latch.countDown();
                }
            }
        });

        // Parse the media
        mediaPlayer.media().parsing().parse();
        try {
            // Wait for the thread to end
            latch.await();
        } catch (InterruptedException e) {
            // Kill the current thread
            Thread.currentThread().interrupt();
            throw new MusicPlayerException("Interrupted while parsing metadata" + e.getMessage());
        }

        if (songMetadata[0] == null) {
            throw new MusicPlayerException("Cannot parse metadata for file : " + filePath);
        }

        // return the first element of the table
        return songMetadata[0];
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
            File defaultImg = new File("/icons/default_song_icon_white.png");
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
