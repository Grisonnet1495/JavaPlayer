package com.javaPlayer.project.model.player;

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
import org.jaudiotagger.tag.datatype.Artwork;
import java.io.File;
import java.util.concurrent.CountDownLatch;


public class MusicPlayer {
    private final MediaPlayerFactory mediaPlayerFactory;
    private final MediaPlayer mediaPlayer;
    private State state;
    private static byte[] imageByte = null;

    public MusicPlayer() {
        mediaPlayerFactory = new MediaPlayerFactory();
        mediaPlayer = mediaPlayerFactory.mediaPlayers().newMediaPlayer(); // Control the music playback
    }

    public SongMetadata getSongMetadata(String filePath) {
        CountDownLatch latch = new CountDownLatch(1);
        final SongMetadata[] metadataHolder = new SongMetadata[1];

        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            public void mediaParsedChanged(MediaPlayer mediaPlayer, MediaParsedStatus status) {
                if (status == MediaParsedStatus.DONE) {
                    String title = mediaPlayer.media().meta().get(Meta.TITLE);
                    String artist = mediaPlayer.media().meta().get(Meta.ARTIST);
                    String album = mediaPlayer.media().meta().get(Meta.ALBUM);
                    String genre = mediaPlayer.media().meta().get(Meta.GENRE);

                    imageByte = imageToBytes(filePath);

                    metadataHolder[0] = new SongMetadata(title, artist, album, genre, imageByte);
                    latch.countDown();
                }
            }
        });

        mediaPlayer.media().prepare(filePath);//call the mediaPlayerEvent

        try {
            latch.await(); // wait the parsing finished
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }

        return metadataHolder[0];
    }

    public static byte[] getImage(String filePath) {
        return imageToBytes(filePath);
    }

    public static byte[] imageToBytes(String filePath) {
        Artwork artwork = null;
        try {
            AudioFile audioFile = AudioFileIO.read(new File(filePath));
            Tag tag = audioFile.getTag();
            if (tag != null && tag.getFirstArtwork() != null) {
                artwork = tag.getFirstArtwork();
                imageByte = artwork.getBinaryData();
            } else {
                File defaultImage = new File("picture/defaultCoverAlbum.png");
                if (defaultImage.exists()) {
                    artwork = (Artwork) ArtworkFactory.createArtworkFromFile(defaultImage);
                    imageByte = artwork.getBinaryData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageByte;
    }


    public void loadAndPlay(String filePath) {
        state = mediaPlayer.status().state();

        if(state == State.STOPPED || state == State.NOTHING_SPECIAL) {
            mediaPlayer.media().play(filePath);
        }
        else if(state == State.PLAYING) {
            Stop();
            mediaPlayer.media().play(filePath);
        }
        else if(state == State.PAUSED) {
            mediaPlayer.controls().play();
        }
    }

    public void play() {
        state = mediaPlayer.status().state();
        if (state == State.PAUSED || state == State.STOPPED) {
            mediaPlayer.controls().play();
        }
    }

    public void pause() {
        state = mediaPlayer.status().state();
        if (state == State.PLAYING) {
            mediaPlayer.controls().pause();
        }
    }

    public void Stop() {
        state = mediaPlayer.status().state();
        if(state == State.PAUSED || state == State.PLAYING) {
            mediaPlayer.controls().stop();
        }
    }

    public void Release() {
        state = mediaPlayer.status().state();
        if(state == State.PAUSED || state == State.PLAYING) {
            mediaPlayer.release();
            mediaPlayerFactory.release();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer.status().isPlaying();
    }

    public void setVolume(int volume) {
        mediaPlayer.audio().setVolume(volume);
    }
}
